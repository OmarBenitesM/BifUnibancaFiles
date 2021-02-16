/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.banbif.daos.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.banbif.beans.LogProceso;
import org.banbif.daos.LogProcesoDao;
import org.banbif.util.Util;
import org.springframework.stereotype.Repository;

/**
 *
 * @author PSSPERU-TI
 */
@Repository
public class LogProcesoDaoFile implements LogProcesoDao {
    
    private List<LogProceso> lLogProceso;
    @Override
    public void create(LogProceso t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LogProceso find(Object id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public List<LogProceso> findAll(Object id, Object id2) {
        CargaLog((String) id, (String) id2);
        return lLogProceso;
    }
    
    @Override
    public void update(List<LogProceso> t, Object id, Object id2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Object id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void CargaLog(String tipoarchivolog, String nombreato){
        boolean accion = false;
        if(tipoarchivolog.contains(".log")){
            //voy por el log directamente para obtener solo la parte del proceso del archivo ato (nomato.dtt)
            try(BufferedReader inputLogFile = new BufferedReader(new FileReader(Util.DIRDIA+tipoarchivolog))){
                String rutabat = "";
                rutabat = Util.DIRDIA + "grabalogaux.txt";
                BufferedWriter bw3;
                File archivo3 = new File(rutabat);
                bw3 = new BufferedWriter(new FileWriter(archivo3));
         
                String line;
                String auxdato = "Starting Transfer Using Request";
                LogProceso logproceso;
                lLogProceso = new ArrayList<>();                
                while((line = inputLogFile.readLine()) != null){
                    if(accion){
                       logproceso = new LogProceso();
                       logproceso.setTextoLog(line + "|");
                       lLogProceso.add(logproceso);
                       bw3.write(line+"\n");
                       if(line.contains(auxdato))
                           accion = false;
                    }
                    
                    if((line.contains(nombreato))){
                       accion = true;
                       logproceso = new LogProceso();
                       logproceso.setTextoLog(line + "|");
                       lLogProceso.add(logproceso);
                       bw3.write(line+"\n");
                    }
                }
                bw3.close();
                inputLogFile.close();
            }catch(FileNotFoundException e){
                Logger.getLogger(LogProcesoDaoFile.class.getName()).log(Level.SEVERE, null, e);
            }catch(IOException e2){
                Logger.getLogger(LogProcesoDaoFile.class.getName()).log(Level.SEVERE, null, e2);
            }
            
        }
        
        if(tipoarchivolog.contains(".txt")){
            // voy por la lista de log generados en el dia
            
        }
    }
}
