package org.banbif.daos.impl;

/**
 *
 * @author PSSPERU-TI Omar Benites
 */
import org.banbif.daos.InventarioDao;
import org.banbif.daos.LogProcesoDao;
public class DaoFactory {
    
    private DaoFactory(){}
    
    public static DaoFactory getInstance(){
        return DaoFactoryHolder.INSTANCE;
    }
    private static class DaoFactoryHolder{
        private static final DaoFactory INSTANCE = new DaoFactory();
    }
    
    public InventarioDao getInventarioDao(){
        return new InventarioDaoFile();
    }
    public LogProcesoDao getLogProcesoDao(){
        return new LogProcesoDaoFile();
    }
}
