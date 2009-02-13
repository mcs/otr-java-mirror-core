package otr.mirror.core.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import otr.mirror.core.dao.RecordingDAO;

/**
 * Implementation of FetchRecordingsService.
 * @author Marcus Krassmann
 */
public class FetchRecordingsServiceImpl implements FetchRecordingsService {

    private static final Log logger = LogFactory.getLog(FetchRecordingsServiceImpl.class);
    
    private RecordingDAO recordingDAO;
    private FileService fileService;

    public void setRecordingDAO(RecordingDAO recordingDAO) {
        this.recordingDAO = recordingDAO;
    }

    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }

    @Override
    public void fetch(String url) {
        HttpClient client = new DefaultHttpClient();
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
