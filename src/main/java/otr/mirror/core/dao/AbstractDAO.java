package otr.mirror.core.dao;

import java.util.List;
import otr.mirror.core.model.AbstractEntity;

/**
 * A generic base implementation of the DAO pattern.
 * 
 * @author Marcus Krassmann
 */
public interface AbstractDAO<T extends AbstractEntity> {

    /** 
     * Persists a business object. Persists all kinds of BusinessObjects.
     * 
     * @param obj the object to be persisted
     */
    void saveOrUpdate(T obj);

    /** 
     * Deletes a business object. Deletes all kinds of BusinessObjects.
     * 
     * @param obj the object to be deleted
     */
    void delete(T obj);

    /**
     * Finds a T by its id.
     * 
     * @param id the id
     * @return the T
     */
    T findById(long id);
    
    /**
     * Finds all existing T.
     * 
     * @return all T
     */
    List<T> findAll();
    
    /**
     * Finds a special T by an example instance.
     * @param entity the example instance of the T
     * @return the matching, persisted instances
     */
    List<T> findByExample(T entity);
    
    /**
     * Finds a unique special T by an example instance.
     * @param entity the example instance of the T
     * @return the unique matching, persisted instance
     */
    T findUniqueByExample(T entity);

}
