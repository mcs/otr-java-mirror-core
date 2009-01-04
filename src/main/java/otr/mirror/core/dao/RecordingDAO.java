package otr.mirror.core.dao;

import java.util.List;
import otr.mirror.core.model.Recording;

/**
 * A DAO interface for recordings.
 * 
 * @author Marcus Krassmann
 */
public interface RecordingDAO extends GenericDAO<Recording> {

    List<Recording> findAllByCreationDate();
}
