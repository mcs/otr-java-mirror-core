package otr.mirror.core.service;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import otr.mirror.core.dao.RecordingDAO;
import java.util.List;
import otr.mirror.core.model.Recording;
import otr.mirror.core.util.FilenameUtil;

/**
 * Standard implementation of ShowRecordingsService.
 * 
 * @author Marcus Krassmann
 */
public class ShowRecordingsServiceImpl implements ShowRecordingsService {

    private RecordingDAO recordingDAO;
    private String storagePath;

    public void setRecordingDAO(RecordingDAO recordingDAO) {
        this.recordingDAO = recordingDAO;
    }

    public void setStoragePath(String storagePath) {
        this.storagePath = storagePath;
    }

    @Override
    public List<Recording> getRecordings() {
        File dir = new File(storagePath);
        File[] otrkeys = dir.listFiles(new FilenameFilter() {

            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith("otrkey");
            }
        });
        otrkeys = otrkeys == null ? new File[0] : otrkeys;
        List<Recording> result = new ArrayList<Recording>();
        for (File each : otrkeys) {
            Recording rec = new Recording(each.getName());
            rec.setAvailable(true);
            rec.setCreationDate(new Date(each.lastModified()));
            rec.setFilesize(each.length());
            result.add(rec);
        }
        Collections.sort(result, new Comparator<Recording>() {

            @Override
            public int compare(Recording o1, Recording o2) {
                return o2.getCreationDate().compareTo(o1.getCreationDate());
            }
        });
        //return recordingDAO.findAllByCreationDate();
        return result;
    }

    @Override
    public File getRecording(String filename) {
        File recording = new File(storagePath, filename);
        return recording.exists() ? recording : null;
    }

}
