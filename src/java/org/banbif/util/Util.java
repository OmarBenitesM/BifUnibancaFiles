package org.banbif.util;
/**
 *
 * @author PSSPERU-TI - Omar Benites
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Util {
    
 public static final String TRANSFER;
 public static final String NOMFILEINV;
 public static final String NOMFINVMAN;
 public static final String EXTNFINV;
 public static final String LNGFINV;
 public static final String EXTNFTTO;
 public static final String DIRDIA;
 public static final String DIRFUENTE;
 public static final String DIRDESTINO;
 public static final String DIRATO;
 public static final String EXTNFDTT;
 public static final String EXTNFFDF;
 public static final String NAMECFGTTO;
 public static final String NUMPRMDTT;
 public static final String NUMPRMTTO;
 public static final String NUMPRMFDF;
 public static final String NAMECFGDTT;
 public static final String NAMECFGFDF;
 public static final String NOMFTTOINV;
 public static final String DIRTTO;
 public static final String DIRALY;
 public static final String DIREREP;
 public static final String DIRBACK;
 public static final String user;
 public static final String pwd;
 public static final String server;
 public static final String nameapp;
 public static final String PROCAUT;
 public static final String PROCMAN;
 public static final String NOMFTTOCTRL;
 public static final String NOMFTTOCNT;
 public static final String LNGFCTRL;
 public static final String LNGFCNT;
 public static final String BIBLIO;
 static {
     ResourceBundle properties = ResourceBundle.getBundle("org.banbif.util.config");
     BIBLIO = properties.getString("biblioteca");
     TRANSFER = properties.getString("transfer");
     NOMFILEINV = properties.getString("nomfileinv");
     NOMFINVMAN = properties.getString("nomfinvman");
     EXTNFINV = properties.getString("extnfinv");
     LNGFINV = properties.getString("lngfinv");
     LNGFCTRL = properties.getString("lngfctrl");
     LNGFCNT = properties.getString("lngfcnt");
     NOMFTTOINV = properties.getString("nomfttoinv");
     EXTNFTTO = properties.getString("extnftto");
     EXTNFDTT = properties.getString("extnfdtt"); 
     EXTNFFDF = properties.getString("extnffdf");
     NUMPRMDTT = properties.getString("numprmdtt");
     NUMPRMFDF = properties.getString("numprmfdf");
     NUMPRMTTO = properties.getString("numprmtto");
     NAMECFGDTT = properties.getString("namecfgdtt");
     NAMECFGFDF = properties.getString("namecfgfdf");
     NAMECFGTTO = properties.getString("namecfgtto");
     DIRDIA = properties.getString("dirdia");
     DIRFUENTE = properties.getString("dirfuente");
     DIRDESTINO = properties.getString("dirdestino");
     DIRATO = properties.getString("dirato");
     DIRTTO = properties.getString("dirtto");
     DIRALY = properties.getString("diraly");
     DIREREP = properties.getString("direrep");
     DIRBACK = properties.getString("dirback");     
     user = properties.getString("user");
     pwd = properties.getString("pwd");
     nameapp = properties.getString("nameapp");
     server = properties.getString("server");
     PROCAUT = properties.getString("procaut");
     PROCMAN = properties.getString("procman");
     NOMFTTOCTRL = properties.getString("nomfttoctrl");
     NOMFTTOCNT = properties.getString("nomfttocnt");
     
 }
 public static String fechaactual(){
     DateTimeFormatter formatter = DateTimeFormatter.BASIC_ISO_DATE;
     String formattedDate = formatter.format(LocalDate.now());
     return formattedDate;
 }
 public static String fecddMMyyy(){
     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
     String formattedDate = formatter.format(LocalDate.now());
     return formattedDate;
 }
 public static String horaactual(){
     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmmss");
     String formattedTime = formatter.format(LocalTime.now());
     return formattedTime;
 }
 public static boolean validafile(String nomfile){
     boolean rpta = false;
     try {
         File archivo = new File(nomfile);
         rpta = archivo.exists();
      }catch(Exception e){
         Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, e);
      }
     return rpta;
 }
 public static boolean validadir(String directorio){
     boolean rpta = false;
     try {
         File dir = new File(directorio);
         if (!dir.exists()){
             if(dir.mkdirs()){
                 rpta = true;
             }
         }else{
             rpta = true;
         }
     }catch(Exception e){
          Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, e);         
     }
     return rpta;
 }
 
 public static String genFileTransfer(String tifilecfg, String nomfileorig, String nomfilecfg, String lngfile, String tipproc, String fechaproceso){
     String archcfg = "";
     String archcfg2 = "";
     String contenido = "";
     String contenido2 = "";
     StringBuilder infoFileConfig, infoFileConfig2;
     
     //arma nombres de los archivos dtt, fdf, tto
     if(tifilecfg.equals(Util.EXTNFTTO)){
        archcfg = Util.DIRTTO + nomfilecfg + tipproc + Util.EXTNFTTO;
        archcfg2 = Util.DIRTTO + nomfilecfg + tipproc + Util.EXTNFFDF;
     }
     
     if(tifilecfg.equals(Util.EXTNFDTT)){
        archcfg = Util.DIRATO + nomfilecfg + Util.EXTNFDTT;
        archcfg2 = Util.DIRATO + nomfilecfg + Util.EXTNFFDF;
     }

     try{
         if(!Util.validafile(archcfg)){
             //si no existe el archivo dtt o tto se carga los parametros de configuracion
             if(tifilecfg.equals(Util.EXTNFTTO)){
                 infoFileConfig = loadFileConfig(Util.NAMECFGTTO, null,Util.EXTNFTTO, nomfilecfg, null, Integer.valueOf(Util.NUMPRMTTO),tipproc,fechaproceso);
                 contenido = infoFileConfig.toString();
             }
             
             if(tifilecfg.equals(Util.EXTNFDTT)){
                 infoFileConfig = loadFileConfig(Util.NAMECFGDTT,null,Util.EXTNFDTT, nomfilecfg, nomfileorig, Integer.valueOf(Util.NUMPRMDTT),tipproc,fechaproceso);
                 contenido = infoFileConfig.toString();                 
             }
             //genera el fdf(file description file)
             infoFileConfig2 = loadFileConfig(Util.NAMECFGFDF, lngfile, Util.EXTNFFDF, null, null, Integer.valueOf(Util.NUMPRMFDF),tipproc,fechaproceso);
             contenido2 = infoFileConfig2.toString();
             
             //crea los archivos
             Files.write(Paths.get(archcfg), contenido.getBytes(),StandardOpenOption.CREATE);
             Files.write(Paths.get(archcfg2), contenido2.getBytes(),StandardOpenOption.CREATE);
         }
     }catch(IOException e){
        Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, e);
     }
     
     return archcfg;  //retorna el nombre del archivo .tto / .dtt lo haya creado o ya existente para ejecutarlo con execruntime
     
 }
 
 public static List<String> execRunTime(List<String> lstFileCng, String fechaproceso){
    // para realizar la copia de todos los archivos se crea un archivo que tendra la lista de los archivos .dtt
    //luego ese archivo se pasa como parametro al comando RXFERPCB
    //variables para encontrar error en la transferencia
     List<String> eval = new ArrayList<>();
     String cad1 = "ha sido CORRECTA"; //para manejar el estado del registro
     String cad2 = "ha FALLADO";     
     String cad3 = "Hora finalizaci";//para enviar al cl OPE120P de as400
     String cad4 = "Filas transferidas"; //para enviar al cl OPE120P de as400
     String cad3aux = null;
    //--
     String fecyhora = ""; 
     fecyhora = fechaproceso;
     
     try{
          //creamos el archivo de lista de .dtt a ejecutar
         String rutat = "";
         rutat = Util.DIRDIA + "cargadtt.lst";
         BufferedWriter bw;
         File archivo = new File(rutat);
         bw = new BufferedWriter(new FileWriter(archivo));
       
         int lngreg = lstFileCng.size();
         String filedtt;
         
         for(int x=0;x<lngreg;x++){             
            filedtt = lstFileCng.get(x);
            bw.write(filedtt + "\n");
         }
         bw.close();
         //creamos el .bat(esto para colocar una sola vez el user y pass y en una isntancia de proceso ejecutar todos los .dtt)
         String rutabat;
         rutabat = Util.DIRDIA + "cargadtt.bat";
         BufferedWriter bw3;
         File archivo3 = new File(rutabat);
         bw3 = new BufferedWriter(new FileWriter(archivo3));
         bw3.write("@echo off\n");
         bw3.write("for /F %%F in ('type " + rutat + "') do (\n");
         bw3.write("echo %date:~4,6%%date:~12,2% %time:~0,8% Starting Transfer Using Request %%F\n");         
         bw3.write("rxferpcb %%F " + Util.user + "	" + Util.pwd + "\n");
         bw3.write("echo.\n");
         bw3.write(")\n");
         bw3.write("echo Transfers completed\n");
         bw3.close();
         
         //creamos el log de salida de la ejecucion
         String rutat2;
         rutat2 = Util.DIRDIA + "LOGFILE_" + fecyhora + ".log";
         String obtnomlog;
         obtnomlog = "LOGFILE_" + fecyhora + ".log";
         BufferedWriter bw2;
         File archivo2 = new File(rutat2);
         bw2 = new BufferedWriter(new FileWriter(archivo2));
         //ejecutando lista de archivos dtt con el comando system i access IBM rxferpcb
         Process p = Runtime.getRuntime().exec("cmd /c " + rutabat);
         // Se obtiene el stream de salida del programa
         InputStream is = p.getInputStream();
            
         /* Se prepara un bufferedReader para poder leer la salida más comodamente. */
         BufferedReader br = new BufferedReader (new InputStreamReader(is));
            
         // Se lee la primera linea
         eval.add(obtnomlog);
         String aux = br.readLine();
            // Mientras se haya leido alguna linea
            while (aux!=null)
            {
                if((aux.contains(cad1)) || (aux.contains(cad2)) || (aux.contains(cad4))){                    
                    eval.add(aux);
                }
                
                if(aux.contains(cad3)){
                        cad3aux = aux;
                        cad3aux = cad3aux.replaceAll("\\.",":");
                        cad3aux = cad3aux.replaceAll("=", "#");
                        eval.add(cad3aux);
                }
                // Se escribe la linea en pantalla
                 bw2.write(aux + "\n");
                // y se lee la siguiente.
                aux = br.readLine();
            }
            bw2.close();
            
        //guardamos la lista de logs que se van generando en el dia 
         String rutat4;
         rutat4 = Util.DIRDIA + "LISTALOG_" + Util.fechaactual() + ".txt";
         
         BufferedWriter bw4;
         File archivo4 = new File(rutat4);
         if(!archivo4.exists()){
             archivo4.createNewFile();
         }         
         bw4 = new BufferedWriter(new FileWriter(archivo4.getAbsoluteFile(),true));
         bw4.write(rutat2+"\n");
         bw4.close();
         
     }catch(IOException e){
         Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, e);
     }catch(Exception ex){
         Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
     }
     return eval;
 }
 
 public static void copyFiles(String origen, String destino){
     
     
        InputStream inputstream;
        OutputStream outputstream;
        
        try {
        File archivoOriginal = new File(origen);
        File archivoCopia = new File(destino);
        
        if(!archivoOriginal.exists()){
            return;
        }
        inputstream = new FileInputStream(archivoOriginal);
        outputstream = new FileOutputStream(archivoCopia);
        byte[] buffer = new byte[1024];
        
        int length;
        while((length = inputstream.read(buffer)) > 0){
            outputstream.write(buffer,0,length);
        }
        inputstream.close();
        outputstream.close();
    }catch(Exception e){
        Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, e);
    }
 }
 
 public static StringBuilder loadFileConfig(String nFileProp, String lngfile, String tfilecfg, String nomibs, 
                                            String nomfte,int numparam, String tipoproc, String fechaproceso){
     String param = "";
     String localfec = fechaproceso.substring(0, 8);     
     StringBuilder sbuilder = new StringBuilder();
     //se obtiene ruta del archivo xml (son 3 archivos) y se trata como obj File
     URL url = Util.class.getResource(nFileProp);     
     String file = url.getPath();
     //con getResourceAsStream nos aseguramos que el recurso sea obtenido como stream
     Properties propiedades = new Properties();     
     try {
         FileInputStream stream = new FileInputStream(file);
         propiedades.loadFromXML(stream);         
         for(int count=0;count<=numparam;count++){
            if(tfilecfg.equals(Util.EXTNFDTT)){
               switch(count){
                   case 5:
                       param = propiedades.getProperty("param"+count);
                       param = param.concat(nomibs);
                       break;
                   case 7:
                       param = propiedades.getProperty("param"+count);
                       param = param.concat(nomibs);
                       break;
                   case 15:
                       param = propiedades.getProperty("param"+count);
                       param = param.concat(nomibs);
                       param = param.concat(Util.EXTNFFDF);
                       break;
                   case 16:
                       param = propiedades.getProperty("param"+count);
                       param = param.concat(nomfte);
                       break;
                   default:
                       param = propiedades.getProperty("param"+count);
                       break;
               }
            }
            
            if(tfilecfg.equals(Util.EXTNFTTO)){
                switch(count){
                   case 4:
                       param = propiedades.getProperty("param"+count);
                       if(nomibs.equals(Util.NOMFTTOINV)){
                          param = param + Util.NOMFTTOINV;
                       }
                       if(nomibs.equals(Util.NOMFTTOCTRL)){
                          param = param + Util.NOMFTTOCTRL;
                       }
                       if(nomibs.equals(Util.NOMFTTOCNT)){
                          param = param + Util.NOMFTTOCNT;
                       }
                       break;
                   case 10:
                       param = propiedades.getProperty("param"+count);
                       if(tipoproc.equals(Util.PROCAUT)){
                          param = param + nomibs + Util.PROCAUT;
                       }
                       if(tipoproc.equals(Util.PROCMAN)){
                          param = param + nomibs + Util.PROCMAN;
                       }
                       param = param + Util.EXTNFFDF;
                       break;
                   case 14:
                       param = propiedades.getProperty("param"+count);
                       if(tipoproc.equals(Util.PROCAUT)){
                         if(nomibs.equals(Util.NOMFTTOINV)){
                            param = param + (Util.NOMFILEINV.concat(localfec));
                            param = param + Util.EXTNFINV;
                         }
                       }
                       if(tipoproc.equals(Util.PROCMAN)){
                         if(nomibs.equals(Util.NOMFTTOINV)){
                            param = param + (Util.NOMFINVMAN.concat(localfec));
                            param = param + Util.EXTNFINV;
                         }
                       }
                       if(nomibs.equals(Util.NOMFTTOCTRL)){
                          param = param + (Util.NOMFTTOCTRL.concat(localfec));
                          param = param + Util.EXTNFINV;
                       }
                       if(nomibs.equals(Util.NOMFTTOCNT)){
                          param = param + (Util.NOMFTTOCNT.concat(localfec));
                          param = param + Util.EXTNFINV;
                       }
                       break;
                   default:
                       param = propiedades.getProperty("param"+count);
                       break;
                }
            }    
            if(tfilecfg.equals(Util.EXTNFFDF)){
                switch(count){
                   case 11:
                       param = propiedades.getProperty("param"+count);
                       param = param + lngfile;
                       break;
                   default:
                       param = propiedades.getProperty("param"+count);
                       break;
               }
            }
            sbuilder.append(param).append("\r\n");
        }           
      }catch (FileNotFoundException ex) {
         Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
      } catch (IOException ex) {
         Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
      }
     return sbuilder;
  }
}
 

