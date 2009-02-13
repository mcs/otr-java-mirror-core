package otr.mirror.core.service;

import otr.mirror.core.model.Recording;

import java.io.File;
import java.util.List;

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
     * @param addDownload if this call should raise the download counter by one
     * @return the file object for the given filename
     */
    File getRecording(String filename, boolean addDownload, String ip);
    
    /**
     * Cancels a download. For example a possibly existing download counter can
     * be decreased or something else can be done. It's up to you...
     *
     * @param file the file whose download was canceled
     */
    void cancelDownload(String file);
}
