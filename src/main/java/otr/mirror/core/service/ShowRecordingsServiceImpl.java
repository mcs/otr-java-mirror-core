package otr.mirror.core.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;
import otr.mirror.core.dao.DownloadDAO;
import otr.mirror.core.dao.RecordingDAO;
import otr.mirror.core.model.Download;
import otr.mirror.core.model.Recording;

import java.io.File;
import java.util.List;

/**
 * Standard implementation of ShowRecordingsService.
 * 
 * @author Marcus Krassmann
 */
public class ShowRecordingsServiceImpl implements ShowRecordingsService {

    private static final Log logger = LogFactory.getLog(ShowRecordingsServiceImpl.class);
    private RecordingDAO recordingDAO;
    private DownloadDAO downloadDAO;
    private FileService fileService;

    public void setRecordingDAO(RecordingDAO recordingDAO) {
        this.recordingDAO = recordingDAO;
    }

    public void setDownloadDAO(DownloadDAO downloadDAO) {
        this.downloadDAO = downloadDAO;
    }

    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }

    @Override
    @Transactional
    public List<Recording> getRecordings() {
        return recordingDAO.findAllByCreationDate();
    }

    @Override
    @Transactional
    public File getRecording(String filename, boolean addDownload, String ip) {
        Recording recording = recordingDAO.findByFilename(filename);
        File file = null;
        if (recording != null && recording.isAvailable()) {
            file = fileService.getFile(filename);
            if (file != null) {
                if (addDownload) {
                    Download download = new Download(recording, ip);
                    downloadDAO.saveOrUpdate(download);
                    recording.addDownload();
                }
            } else {
                recording.setAvailable(false);
                logger.warn("File not found: " + filename + "; flagged as unavailable.");
            }
        }
        return file;
    }

    @Override
    @Transactional
    public void cancelDownload(String filename) {
        Recording recording = recordingDAO.findByFilename(filename);
        File file = null;
        if (recording != null && recording.isAvailable()) {
            file = fileService.getFile(filename);
            if (file != null) {
                recording.setDownloaded(recording.getDownloaded() - 1);
            } else {
                recording.setAvailable(false);
                logger.warn("File not found: " + filename + "; flagged as unavailable.");
            }
        }
    }
}
