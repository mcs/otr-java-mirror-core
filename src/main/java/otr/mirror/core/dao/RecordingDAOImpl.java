package otr.mirror.core.dao;

import java.util.List;
import javax.persistence.Query;
import otr.mirror.core.model.Recording;

/**
 * JPA implementation of RecordingDAO.
 *
 * @author Marcus Krassmann
 */
public class RecordingDAOImpl extends AbstractDAOImpl<Recording> implements RecordingDAO {

    @Override
    @SuppressWarnings("unchecked")
    public List<Recording> findAllByCreationDate() {
        Query qry = em.createQuery("FROM Recording ORDER BY creationDate DESC");
        return qry.getResultList();
    }

}
