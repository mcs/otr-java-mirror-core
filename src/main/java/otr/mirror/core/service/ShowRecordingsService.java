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

    List<Recording> getRecordings();

    File getRecording(String filename);
    
}
