/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.banbif.daos.impl;

import java.util.List;
import org.banbif.beans.Inventario;
import org.banbif.daos.InventarioDao;
import org.banbif.util.Util;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Repository;
/**
 *
 * @author PSSPERU-TI Omar Benites
 */
@Repository
public class InventarioDaoFile implements InventarioDao{
    
    private List<Inventario> lInventario;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HH:mm:ss");
    @Override
    public void create(Inventario t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Inventario find(Object id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Inventario> findAll(Object id, Object id2){
         cargaInventario((String)id, (String)id2);
         return lInventario;
    }
    
    @Override
    public void update(List<Inventario> t, Object nomfile, Object fecyhorproc){
       String fecprocctrl = ((String)(fecyhorproc)).substring(0, 8);
       
       String fichero = Util.DIRDIA + (String)nomfile + fecprocctrl + Util.EXTNFINV;
       String fichero2 = Util.DIRDIA + (String)nomfile + fecprocctrl + Util.horaactual() + Util.EXTNFINV;
       try(BufferedWriter bufOutput = new BufferedWriter(new FileWriter(fichero))){
           String Line;
           String fecproc;
           String timeproc;
           String lngreg;
           LocalDate localDate = LocalDate.now();
           for(Inventario inventario:t){
               LocalTime localTime = LocalTime.now();
               fecproc = localDate.format(formatter);
               timeproc = localTime.format(formatter2);
               lngreg = String.valueOf(inventario.getLongreg());
               Line = inventario.getCodsecbanco()+","+
               inventario.getNombreorigen()+","+
               inventario.getDescripcion()+","+
               inventario.getNombreato()+","+
               lngreg+","+
               inventario.getIndrecep()+","+
               inventario.getTabperiodopc()+","+
               fecproc+","+ 
               timeproc+","+
               Util.user+","+
               Util.nameapp+","+
               inventario.getEstadoreg()+","+
               inventario.getLogproceso()+",X"; 
               bufOutput.write(Line);
               bufOutput.newLine();
           }
           bufOutput.close();
           Util.copyFiles(fichero, fichero2);
       }catch(IOException e){
           Logger.getLogger(InventarioDaoFile.class.getName()).log(Level.SEVERE, null, e);
       }
    }

    @Override
    public void delete(Object id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public void cargaInventario(String nomfileproc, String fecyhorproc){
        String fichero;
        String fecproc = fecyhorproc.substring(0, 8);
        fichero = Util.DIRDIA + nomfileproc + fecproc + Util.EXTNFINV;
        System.out.println("obm fichero: " + fichero);
        try(BufferedReader inputINV = new BufferedReader(new FileReader(fichero))){
             
            String line;
            Inventario inventario;
            String estadoreg;
            lInventario = new ArrayList<>();
            
            while((line = inputINV.readLine()) != null){
                if(line.length()>1){
                StringTokenizer tokens = new StringTokenizer(line,",");
                inventario = new Inventario();
                inventario.setCodsecbanco(tokens.nextToken().trim());
                inventario.setNombreorigen(tokens.nextToken().trim());
                inventario.setDescripcion(tokens.nextToken().trim());
                inventario.setNombreato(tokens.nextToken().trim());
                inventario.setLongreg(tokens.nextToken().trim());
                inventario.setIndrecep(tokens.nextToken().trim());
                inventario.setTabperiodopc(tokens.nextToken().trim());
                inventario.setFecharegistro(LocalDate.parse(tokens.nextToken().trim(),formatter));
                inventario.setHoraregistro(LocalTime.parse(tokens.nextToken().trim()));
                inventario.setUsuarireg(tokens.nextToken().trim());
                inventario.setProgramreg(tokens.nextToken().trim());
                inventario.setEstadoreg(tokens.nextToken().trim());
                inventario.setLogproceso(tokens.nextToken().trim());
                lInventario.add(inventario);
                }
            }
        }
            catch(FileNotFoundException e){
              Logger.getLogger(InventarioDaoFile.class.getName()).log(Level.SEVERE, null, e);           
            }catch(IOException e2){
                Logger.getLogger(InventarioDaoFile.class.getName()).log(Level.SEVERE, null, e2);
            }
    }
}
