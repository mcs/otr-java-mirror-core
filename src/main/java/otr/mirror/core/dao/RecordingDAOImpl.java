package otr.mirror.core.dao;

import org.springframework.transaction.annotation.Transactional;
import otr.mirror.core.model.Recording;

import javax.persistence.Query;
import java.util.Date;
import java.util.List;

/**
 * JPA implementation of RecordingDAO.
 *
 * @author Marcus Krassmann
 */
public class RecordingDAOImpl extends AbstractDAOImpl<Recording> implements RecordingDAO {

    @Override
    @SuppressWarnings("unchecked")
    public List<Recording> findAllByCreationDate() {
        Query qry = em.createQuery("FROM Recording WHERE available = :available ORDER BY startTime DESC");
        qry.setParameter("available", true);
        return qry.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Recording> findAll(boolean available) {
        Query qry = em.createQuery("FROM Recording WHERE available = :available");
        qry.setParameter("available", available);
        return qry.getResultList();
    }

    @Override
    @Transactional
    public Recording findByFilename(String filename) {
        Query qry = em.createQuery("FROM Recording WHERE filename = :filename");
        qry.setParameter("filename", filename);
        try {
            return (Recording) qry.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Recording> findModifiedOlderThan(Date date) {
        Query qry = em.createQuery("FROM Recording WHERE modifiedDate < :date");
        qry.setParameter("date", date);
        return qry.getResultList();
    }

}
