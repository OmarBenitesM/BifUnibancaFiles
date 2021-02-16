/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.banbif.service;

import java.util.List;
import org.banbif.beans.LogProceso;

/**
 *
 * @author PSSPERU-TI
 */
public interface LogProcesoService {
 public List<LogProceso> obtLogFile(String nomfilelog, String filenomato);   
}
