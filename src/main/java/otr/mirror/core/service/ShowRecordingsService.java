package otr.mirror.core.service;

import java.io.File;
import java.util.List;
import otr.mirror.core.model.Recording;

/**
 * A service for showing files.
 * 
 * @author Marcus Krassmann
 */
public interface ShowRecordingsService {

    /**
     * Gets a list of all existing recordings.
     * 
     * @return all recordings
     */
    List<Recording> getRecordings();

    /**
     * Gets a file object pointing to the real file.
     * 
     * @param filename the filename
     * @return the file object for the given filename
     */
    File getRecording(String filename);
    
}
