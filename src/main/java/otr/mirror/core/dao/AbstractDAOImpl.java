package otr.mirror.core.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import otr.mirror.core.model.AbstractEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * An implementation of the GenericDAO.
 * 
 * @author Marcus Krassmann
 */
public abstract class AbstractDAOImpl<T extends AbstractEntity> implements AbstractDAO<T> {

    private static final Log logger = LogFactory.getLog(AbstractDAO.class);

    @PersistenceContext
    protected EntityManager em;

    protected Session getSession() {
        logger.debug("Called getSession()");
        Session session = (Session) em.getDelegate();
        if (!session.isOpen()) {
            logger.error("Session's not open: " + session);
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
    public T findById(long id) {
        return em.find(getEntityClass(), id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        logger.debug("EntityManager = " + em);
        Criteria crit = getSession().createCriteria(getEntityClass());
        return crit.list();
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
        else if (t instanceof Class) {
            return getGenericParameterClass((Class) t);
        }

        return null;
    }
}
