package otr.mirror.core.dao;

import java.util.List;
import otr.mirror.core.model.Recording;

/**
 * JPA implementation of RecordingDAO.
 *
 * @author Marcus Krassmann
 */
public class RecordingDAOImpl extends GenericDAOImpl<Recording> implements RecordingDAO {

    @Override
    public List<Recording> findAllByCreationDate() {
        return getJpaTemplate().find("FROM Recording ORDER BY creationDate DESC");
    }

}
