/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.banbif.service.serviceImpl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.banbif.beans.Inventario;
import org.banbif.beans.UnlFilesParam;
import org.banbif.daos.InventarioDao;
import org.banbif.daos.impl.DaoFactory;
import org.banbif.service.InventarioService;
import org.banbif.util.CnxAS400;
import org.banbif.util.Util;
import org.springframework.stereotype.Service;


/**
 *
 * @author PSSPERU-TI - Omar Benites
 */
@Service
public class InventarioServiceImpl implements InventarioService {
    private InventarioDao dao;
    
    public InventarioServiceImpl(){
        DaoFactory fabrica = DaoFactory.getInstance();
        dao = fabrica.getInventarioDao();
    }

    @Override
    public List<Inventario> transferProc(String fecyhorctrl) {
        String nomfilecfg;
        List<String> execDTT = new ArrayList<>(); //lista auxiliar con los DTT a ejecutar
        List<String> result = new ArrayList<>();
        int tot;
        List<Inventario> listaproceso = dao.findAll(Util.NOMFILEINV, fecyhorctrl); //carga de archivo en memoria
 
        //proceso de copia de archivos
        for(Inventario a:listaproceso){
         if(a.getEstadoreg().equals("A")){ //procesar archivos pendientes
            try{
             if(Util.validafile(Util.DIRFUENTE.concat(a.getNombreorigen()))){ //valida existencia de archivo unibanca en ruta origen
                 
                Util.copyFiles(Util.DIRFUENTE.concat(a.getNombreorigen()),
                               Util.DIRDESTINO.concat(a.getNombreorigen()));//genera copia a carpeta \bif
                
                nomfilecfg = Util.genFileTransfer(Util.EXTNFDTT, a.getNombreorigen(), a.getNombreato(), a.getLongreg(),null,fecyhorctrl); //valida si existe .dtt o .tto si no existe genera archivo con su fdf asociado
                execDTT.add(nomfilecfg);
             }
            }catch (Exception ex){
                Logger.getLogger(InventarioServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
         }         
        }
        if(execDTT.size()>0){
         
            result = Util.execRunTime(execDTT, fecyhorctrl);// ejecuta archivos .dtt para transferir a IBS retornando si hubiera errores en una matriz
            tot = result.size();
            String cadres;
            String nmarch = null;
            String nreg = null; //nro de registros transferidos
            int ini2, ini3; // para obtener el nro de registros obtenidos
            int fin2, fin3;
            String feclogcpy = null;
            String horlogcpy = null;
            String yyyy,hhh;
            String mm, mmm;
            String dd, sss;
            String paramcl = null; //armar la cadena de parametros para el cl OPE001U en as400
            String estreg = null;
            String estregnum = null;
            List<String> parclas400 = new ArrayList<>();
            String cad = "FALLADO";
            String cad1 = "CORRECTA";
            String cad2 = "La petición de transferencia " + Util.DIRDIA;
            String datperiyopc = null;
            int fin;
            int ini = cad2.length();       
            String nomlog = result.get(0);
            for(int i=1;i<tot;i++){            
                cadres = result.get(i);
                System.out.println("cadres : " + result.get(i));
                fin = result.get(i).indexOf(".");
                  if(fin != -1){                      
                    nmarch = result.get(i).substring(ini,fin);
                    for(Inventario b:listaproceso){                
                      if(b.getNombreato().equals(nmarch)){
                         datperiyopc = b.getTabperiodopc().substring(5); //para obtener la opc a la que pertenece el file
                         if(cadres.trim().contains(cad1.trim())){
                           b.setEstadoreg("P");
                           estreg = "P";
                         } 
                         if(cadres.trim().contains(cad.trim())){
                           b.setEstadoreg("E");
                           estreg = "E";
                         }
                      b.setLogproceso(nomlog);
                    }
                }
                }else{
                      //informacion que está en el log  capturado LOGFILE_fechahora                      
                      ini2 = result.get(i).indexOf("#");
                      if(ini2 != -1){
                              System.out.println("validando si parametrea: " + result.get(i));
                              //fecha y hora de proceso del servidor al copiar los archivos
                              ini3 = result.get(i).lastIndexOf(' ');
                              fin3 = result.get(i).lastIndexOf(":");
                              feclogcpy = cadres.substring(ini2 + 1, ini3);                              
                              horlogcpy = cadres.substring(ini3 + 1, fin3);
                              //formateando fecha a yyyymmdd
                              ini2 = feclogcpy.indexOf("-");
                              ini3 = feclogcpy.lastIndexOf("-");
                              yyyy = feclogcpy.substring(1, ini2);
                              mm = feclogcpy.substring(ini2+1, ini3);
                              dd = feclogcpy.substring(ini3 + 1);
                              mm = String.format("%2s", mm).replace(' ', '0');
                              dd = String.format("%2s", dd).replace(' ', '0');
                              feclogcpy = yyyy.concat(mm);
                              feclogcpy = feclogcpy.concat(dd);
                              //formateando hora 00:00:00
                              ini2 = horlogcpy.indexOf(":");
                              ini3 = horlogcpy.lastIndexOf(":");
                              hhh = horlogcpy.substring(0,ini2);
                              mmm = horlogcpy.substring(ini2 + 1,ini3);
                              sss = horlogcpy.substring(ini3 + 1);
                              hhh = String.format("%2s", hhh).replace(' ', '0');
                              mmm = String.format("%2s", mmm).replace(' ', '0');
                              sss = String.format("%2s", sss).replace(' ', '0');
                              horlogcpy = hhh.concat(mmm);
                              horlogcpy = horlogcpy.concat(sss);                              
                      }else{
                          //nro registros copiados
                          ini2 = result.get(i).indexOf("=");
                          if(ini2 != -1){
                             fin2 = cadres.length();
                             nreg = cadres.substring(ini2 + 1, fin2);
                             nreg = String.format("%7s", nreg).replace(' ', '0');
                             //setear estado del registro (que viene de la 1era lectura)
                             if(estreg.equals("P"))
                                estregnum = "1";
                             if(estreg.equals("E"))
                                estregnum = "2";
                             //arma el comando a enviar                                            
                              paramcl = "'" + nmarch + "' '" + nreg + "' '" + estregnum + "' '" + datperiyopc + 
                                      "' '" + feclogcpy + "' '" + horlogcpy + "' '" + fecyhorctrl + "' '" + Util.user + "' '" + 
                                      Util.nameapp + "'";
                              System.out.println("PARAMETROS A ENVIAR: " + paramcl);
                              parclas400.add(paramcl);
                          }
                      }
                  }
            }

            dao.update(listaproceso, Util.NOMFILEINV, fecyhorctrl);  //regenera el archivo inventario
            //ejecutando el cl OPE001U para actualizar informacion de los archivos en el schedule
            String commandCl;
            CnxAS400 command = new CnxAS400();
            for(int cont=0;cont<parclas400.size();cont++){
                commandCl = "CALL " + Util.BIBLIO + "/OPE120P PARM("+ parclas400.get(cont)+")";
                System.out.println("comando AS400: " + commandCl);
                command.ejcutarPeticion(Util.server, Util.user, Util.pwd, commandCl);
            }
        }
        List<Inventario> listaproc = dao.findAll(Util.NOMFILEINV, fecyhorctrl);
        return listaproc;
    }
    
    @Override
    public boolean unloadFilesBases(List<UnlFilesParam> fileproceso, String tipoproc, String fechaproceso){
        
        boolean rpta = false;
        
        List<String> execTTO;      
        String nomfilecfg;    
        String nomfilproc = "";
        String lFecproc = fechaproceso.substring(0, 8);
        //proceso de descarga de inventario de archivos del ibs
        if(tipoproc.equals(Util.PROCAUT)){
            nomfilproc = Util.NOMFILEINV + lFecproc + Util.EXTNFINV;
        }
        if(tipoproc.equals(Util.PROCMAN)){
            nomfilproc = Util.NOMFINVMAN + lFecproc + Util.EXTNFINV;
        }
        if(Util.validadir(Util.DIRDIA)){
          if(Util.validadir(Util.DIRTTO)){
             if(!Util.validafile(Util.DIRDIA + nomfilproc)){
                System.out.println("matriz TTO :" + fileproceso.toString());
                 System.out.println("el nomfil: " + nomfilproc);
                execTTO = new ArrayList<>();
                
                for(int z=0;z<fileproceso.size();z++){               
                    nomfilecfg = Util.genFileTransfer(Util.EXTNFTTO, null, fileproceso.get(z).getNomfile(), fileproceso.get(z).getLongitud(), tipoproc,fechaproceso);
                    execTTO.add(nomfilecfg);
                }
                
                if(!execTTO.isEmpty()){
                   List<String> resunl = Util.execRunTime(execTTO, fechaproceso);
                   String cadx  = "FALLADO";
                   String cadx2 = "CORRECTA";
                   String cadtot;
                   for(int i=1;i<resunl.size();i++){
                      cadtot = resunl.get(i);
                      if(cadtot.trim().contains(cadx2.trim())){
                         rpta = true;
                      } 
                      if(cadtot.trim().contains(cadx.trim())){
                         rpta = false;
                      }
                   }
                }
             }else {
               rpta = true;
               }
           }
        }
        
       return rpta;
    }

    @Override
    public List<Inventario> getAllInv() {
        //Util.NOMFTTOINV
        
        List<Inventario> listaeven = dao.findAll(Util.NOMFINVMAN, Util.fechaactual()+Util.horaactual());
        return listaeven;
    }

    @Override    
    public List<Inventario> transferEventual(List<String> listaevt) {
        String nomfileato;
        String nomfileorigen;
        String longreg;
        String nomfilecfg;
        int pos,pos2;
        int lng;
        List<Inventario> listatotal = null;
        List<String> execDTTevt = new ArrayList<>(); //lista auxiliar con los DTT a ejecutar
        List<String> result = null;
        System.out.println("obm eventual: " + listaevt.toString());
        for(int x=0;x<listaevt.size();x++){

            pos = listaevt.get(x).indexOf("-");
            pos2 = listaevt.get(x).indexOf("_");
            lng = listaevt.get(x).length();            
            nomfileorigen = listaevt.get(x).substring(0,pos);
            nomfileato = listaevt.get(x).substring(pos+1,pos2);
            longreg = listaevt.get(x).substring(pos2+1, lng);
            System.out.println("obm eventual: " + nomfileorigen + ' ' + nomfileato + ' ' + longreg);
            if(Util.validafile(Util.DIRFUENTE.concat(nomfileorigen))){
               Util.copyFiles(Util.DIRFUENTE.concat(nomfileorigen), Util.DIRDESTINO.concat(nomfileorigen));
               nomfilecfg = Util.genFileTransfer(Util.EXTNFDTT, nomfileorigen, nomfileato, longreg,Util.PROCMAN,Util.fechaactual());
               execDTTevt.add(nomfilecfg);
            }
        }
        if(execDTTevt.size()>0){
           result = Util.execRunTime(execDTTevt,Util.fechaactual().concat(Util.horaactual()));// ejecuta archivos .dtt para transferir a IBS retornando si hubiera errores en una matriz
            int tot = result.size();
            String cadres;
            String nmarch;
            int fin;
            String cad = "FALLADO";
            String cad1 = "CORRECTA";
            String cad2 = "La petición de transferencia " + Util.DIRDIA;
            int ini = cad2.length();       
            String nomlog = result.get(0);
            listatotal = dao.findAll(Util.NOMFINVMAN, Util.fechaactual()+Util.horaactual());
            for(int i=1;i<tot;i++){            
                cadres = result.get(i);
                fin = result.get(i).indexOf(".");
                if(fin != -1){                
                nmarch = result.get(i).substring(ini,fin);
                
                for(Inventario b:listatotal){                
                    if(b.getNombreato().equals(nmarch)){

                      if(cadres.trim().contains(cad1.trim()))
                         b.setEstadoreg("P");

                      if(cadres.trim().contains(cad.trim()))
                         b.setEstadoreg("E");

                      b.setLogproceso(nomlog);
                    }
                }
                }
            }

            dao.update(listatotal, Util.NOMFINVMAN, Util.fechaactual());  //regenera el archivo inventario
        }
        List<Inventario> listaproc = dao.findAll(Util.NOMFINVMAN, Util.fechaactual());
        return listaproc;
    }

    @Override
    public String valFecControl(String fileoperctrl) {
        String fecctrl = null;
        String auxfecctrl = null;
        String horacontrol = null;
        String feccntctrl = null;
        String diafecnt = null;
        String mesfecnt = null;
        String aniofecnt = null;
        String filecontrol = Util.DIRDIA + fileoperctrl + Util.fechaactual() + Util.EXTNFINV;        
       
        try(BufferedReader infilctrl = new BufferedReader(new FileReader(filecontrol))){
             
            String line;
            
            while((line = infilctrl.readLine()) != null){
                if(line.length()> 1){
                   fecctrl = line.substring(35, 51);
                   auxfecctrl = line.substring(35, 43);
                   horacontrol = line.substring(44, 50);
                }
            }
        }
            catch(FileNotFoundException e){
              Logger.getLogger(InventarioServiceImpl.class.getName()).log(Level.SEVERE, null, e);           
            }catch(IOException e2){
                Logger.getLogger(InventarioServiceImpl.class.getName()).log(Level.SEVERE, null, e2);
            }
        
        System.out.println("fec aper: " + fecctrl);
        fecctrl = auxfecctrl.concat(horacontrol);
        return fecctrl;
    }
}
