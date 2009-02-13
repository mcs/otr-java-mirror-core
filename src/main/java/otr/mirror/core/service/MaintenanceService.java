package otr.mirror.core.service;

import otr.mirror.core.model.Recording;

import java.io.File;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Marcus Krassmann
 */
public interface MaintenanceService {

    /**
     * Checks the file system for recordings not mapped by the database.
     * 
     * If an orphan is found, its file size will be compared to the
     * "should-be file size". Depending on this result, the following actions
     * may happen:
     * <ol>
     * <li>
     * If sizes differ, the orphan will be moved to a special orphen
     * directory.
     * </li>
     * <li>
     * If sizes match, the orphan will be added to the recordings list.
     * </li>
     * </ol>
     *
     * @return all found orphans
     */
    Set<File> checkForFileOrphans();

    /**
     * Checks the database for non-existing recordings.
     * 
     * @return all recordings not existing on the file system
     */
    List<Recording> checkForDBOrphans();

    /**
     * Removes all recordings (DB and file system) that have not been downloaded
     * for many days.
     *
     * @return all recordings that have been removed
     */
    List<Recording> removeOldRecordings();

}
