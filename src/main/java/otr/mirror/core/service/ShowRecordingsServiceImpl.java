package otr.mirror.core.service;

import java.io.File;
import otr.mirror.core.dao.RecordingDAO;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import otr.mirror.core.model.Recording;

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
    @Transactional
    public List<Recording> getRecordings() {
//        File dir = new File(storagePath);
//        File[] otrkeys = dir.listFiles(new FilenameFilter() {
//
//            @Override
//            public boolean accept(File dir, String name) {
//                return name.endsWith("otrkey");
//            }
//        });
//        otrkeys = otrkeys == null ? new File[0] : otrkeys;
//        List<Recording> result = new ArrayList<Recording>();
//        Recording rec;
//        for (File each : otrkeys) {
//            rec = new Recording(each.getName());
//            rec.setAvailable(true);
//            rec.setCreationDate(new Date(each.lastModified()));
//            rec.setFilesize(each.length());
//            result.add(rec);
//        }
//        Collections.sort(result, new Comparator<Recording>() {
//
//            @Override
//            public int compare(Recording o1, Recording o2) {
//                return o2.getStartTime().compareTo(o1.getStartTime());
//            }
//        });
        return recordingDAO.findAllByCreationDate();
//        return result;
    }

    @Override
    public File getRecording(String filename) {
        File recording = new File(storagePath, filename);
        return recording.exists() ? recording : null;
    }

}
