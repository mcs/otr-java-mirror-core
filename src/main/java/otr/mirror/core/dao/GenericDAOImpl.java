package otr.mirror.core.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.springframework.orm.jpa.support.JpaDaoSupport;
import otr.mirror.core.model.BusinessObject;

/**
 * An implementation of the GenericDAO.
 * 
 * @author Marcus Krassmann
 */
public abstract class GenericDAOImpl<T extends BusinessObject> extends JpaDaoSupport implements GenericDAO<T> {

    private static final Logger log = Logger.getLogger(GenericDAO.class);

    @PersistenceContext
    protected EntityManager em;

    protected Session getSession() {
        log.debug("Called getSession()");
        Session session = (Session) em.getDelegate();
        if (!session.isOpen()) {
            log.error("Session's not open: " + session);
        }
        return session;
    }

    @Override
    public void saveOrUpdate(T obj) {
        em.persist(obj);
    }

    @Override
    public void delete(T obj) {
        em.remove(obj);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T findById(long id) {
        return em.find(getEntityClass(), id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        log.debug("EntityManager = " + em);
        Criteria crit = getSession().createCriteria(getEntityClass());
        return (List<T>) crit.list();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<T> findByExample(T obj) {
        Example ex = Example.create(obj);
        return (List<T>) getSession().createCriteria(getEntityClass()).add(ex).list();
    }

    @Override
    public T findUniqueByExample(T entity) {
        List<T> entities = findByExample(entity);
        if (entities.isEmpty()) {
            return null;
        }
        else if (entities.size() > 1) {
            throw new RuntimeException("Result not unique! Found " + entities);
        }
        return entities.get(0);
    }

    /**
     * Get the class of the entity, specified as generic parameter.
     *
     * @return the entity class for this DAO, or <tt>null</tt> if none was found
     */
    protected Class<T> getEntityClass() {
        return getGenericParameterClass(this.getClass());
    }

    /**
     * Some generic magic ;-)
     * <br>
     * Returns the type of the generic declaration of the superclass.
     * Check out Neal Gafter's "Super Type Token" idiom for more information.
     *
     * @return type of generic declaration of superclass
     */
    private Class<T> getGenericParameterClass(Class clazz) {
        Type t = clazz.getGenericSuperclass();

        if (t instanceof ParameterizedType) {
            // Class has generic parmeters
            Type t2 = ((ParameterizedType) t).getActualTypeArguments()[0];
            if (t2 instanceof Class) {
                // found class, return it
                return (Class) t2;
            }
            // clazz was directly generic typed, t2 is generic TypeVariable
            return null;
        }

        if (t instanceof Class) {
            return getGenericParameterClass((Class) t);
        }

        return null;
    }
}
