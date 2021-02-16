package org.banbif.daos;

/**
 *
 * @author PSSPERU-TI - Omar Benites
 * @param <T>
 */
import java.util.List;
public interface EntidadDao <T>{
    public void create(T t);
    
    public T find(Object id);
    public List<T> findAll(Object id, Object id2);
    public void update(List<T> t, Object id, Object id2);
    public void delete(Object id);
}
