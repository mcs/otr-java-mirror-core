package otr.mirror.core.dao;

import otr.mirror.core.model.Recording;

import java.util.Date;
import java.util.List;

/**
 * A DAO interface for recordings.
 * 
 * @author Marcus Krassmann
 */
public interface RecordingDAO extends AbstractDAO<Recording> {

    /**
     * Finds all recordings, ordered descending by creation date.
     * 
     * @return all recordings ordered descending by creation date
     */
    List<Recording> findAllByCreationDate();

    /**
     * Finds either all available or all not available recordings.
     *
     * @param available the availability of the recordings
     * @return all recordings matching the chosen availability
     */
    List<Recording> findAll(boolean available);

    Recording findByFilename(String filename);

    List<Recording> findModifiedOlderThan(Date date);
}
