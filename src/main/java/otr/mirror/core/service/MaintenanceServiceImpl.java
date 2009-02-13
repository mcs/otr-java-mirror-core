package otr.mirror.core.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.transaction.annotation.Transactional;
import otr.mirror.core.dao.RecordingDAO;
import otr.mirror.core.model.Recording;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 *
 * @author Marcus Krassmann
 */
public class MaintenanceServiceImpl implements MaintenanceService {

    private static final Log logger = LogFactory.getLog(MaintenanceServiceImpl.class);
    
    private RecordingDAO recordingDAO;
    private FileService fileService;

    public void setRecordingDAO(RecordingDAO recordingDAO) {
        this.recordingDAO = recordingDAO;
    }

    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }

    @Override
    @Transactional
    public Set<File> checkForFileOrphans() {
        long startTime = System.currentTimeMillis();
        List<Recording> recordings = recordingDAO.findAll(true);
        logger.debug("Found " + recordings + " complete recordings.");
        Set<String> filenames = new HashSet<String>();
        for (Recording each : recordings) {
            filenames.add(each.getFilename());
        }

        File[] otrkeys = fileService.getAllFiles();
        logger.debug("Found " + otrkeys.length + " OTRKEY files.");
        Set<File> otrkeySet = new HashSet<File>(Arrays.asList(otrkeys));

        // remove all registered OTRKEYs
        Iterator<File> it = otrkeySet.iterator();
        while (it.hasNext()) {
            File each = it.next();
            if (filenames.contains(each.getName())) {
                logger.debug("Found persisted recording " + each.getName());
                it.remove();
            }
        }

        logger.debug("Found " + otrkeySet.size() + " orphened OTRKEY files.");
        if (otrkeySet.size() > 0) {
            logger.info("Persisting these files: " + otrkeySet);
            // create http client for comparing filesizes with external source
            HttpClient client = new DefaultHttpClient();
            for (File each : otrkeySet) {
                logger.debug("Requesting filesize for file " + each.getName());
                HttpUriRequest req = new HttpGet("http://otrinfo.de/filesize/" + each.getName());
                InputStream is;
                int bufferSize = 1024;
                byte[] content = new byte[bufferSize];
                try {
                    is = client.execute(req).getEntity().getContent();
                    is.read(content, 0, bufferSize);
                } catch (IOException ex) {
                    logger.error("Filesize comparison not possible, otrinfo.de down?", ex);
                    return null;
                }
                String strSize = new String(content).trim();
                logger.debug(strSize.length() + "characters in String");
                long size;

                try {
                    size = Long.parseLong(strSize);
                } catch (NumberFormatException ex) {
                    logger.error("Could not retrieve filesize from server answer!", ex);
                    return null;
                }

                // evaluate answer from server
                if (size == each.length()) {
                    // add orphen  to database
                    Recording recording = new Recording(each.getName());
                    recording.setFilesize(size);
                    recording.setAvailable(true);
                    recordingDAO.saveOrUpdate(recording);
                    logger.info("Persisted orphened recording: " + recording);
                } else {
                    // move orphen to a special directory
                    logger.warn("Moved orphened file with wrong filesize into orphen-folder: " + each);
                    boolean rc = fileService.moveToOrphenFolder(each);
                    if (rc) {
                        logger.debug("Orphened file successfully moved to orphen directory.");
                    } else {
                        logger.error("Could not move '" + each + "' to orphen folder!");
                    }
                }
            }
        }
        long stopTime = System.currentTimeMillis();
        logger.debug("Duration of check for orphened files: " + (stopTime - startTime) + "ms");
        return otrkeySet;
    }

    @Override
    @Transactional
    public List<Recording> checkForDBOrphans() {
        List<Recording> recordings = recordingDAO.findAll(true);

        // remove all recordings that really exist as file
        Recording eachRec;
        File eachFile;
        Iterator<Recording> it = recordings.iterator();
        while (it.hasNext()) {
            eachRec = it.next();
            eachFile = fileService.getFile(eachRec.getFilename());
            if (eachFile != null) {
                it.remove();
            }
        }

        // now recordings holds all database orphans
        for (Recording each : recordings) {
            logger.warn("Removed orphened db-record: " + each);
            each.setAvailable(false);
        }
        return recordings;
    }

    @Override
    @Transactional
    public List<Recording> removeOldRecordings() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -30);
        List<Recording> recordings = recordingDAO.findModifiedOlderThan(cal.getTime());
        for (Recording each : recordings) {
            boolean rc = fileService.delete(each.getFilename());
            if (!rc) {
                logger.warn("Could not delete file of old recording " + each);
            }
            recordingDAO.delete(each);
        }
        return recordings;
    }
}
