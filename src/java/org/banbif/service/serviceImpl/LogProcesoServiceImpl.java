/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.banbif.service.serviceImpl;

import java.util.List;
import org.banbif.beans.LogProceso;
import org.banbif.daos.LogProcesoDao;
import org.banbif.daos.impl.DaoFactory;
import org.banbif.service.LogProcesoService;
import org.springframework.stereotype.Service;

/**
 *
 * @author PSSPERU-TI
 */
@Service
public class LogProcesoServiceImpl implements LogProcesoService{
    private LogProcesoDao dao;

    public LogProcesoServiceImpl() {
        DaoFactory fabrica = DaoFactory.getInstance();
        dao = fabrica.getLogProcesoDao();
    }

    @Override
    public List<LogProceso> obtLogFile(String nomfilelog, String filenomato) {
        List<LogProceso> logprocfile = dao.findAll(nomfilelog, filenomato);
        return logprocfile;
    }
    
}
