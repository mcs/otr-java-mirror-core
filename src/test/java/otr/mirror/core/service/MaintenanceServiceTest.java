
package otr.mirror.core.service;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import otr.mirror.core.OtrTests;
import otr.mirror.core.dao.RecordingDAO;
import otr.mirror.core.model.Recording;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.*;

/**
 *
 * @author marcus
 */
public class MaintenanceServiceTest extends OtrTests {

    @Autowired private MaintenanceService maintenanceService;
    @Autowired private RecordingDAO recordingDAO;
    private File storageDir;

    @Before
    public void onSetUpInTransaction() throws Exception {
        storageDir = new File(getStoragePath());
    }

    private String getStoragePath() {
        Properties props = new Properties();
        try {
            props.load(new ClassPathResource("otr.properties").getInputStream());
        } catch (IOException ex) {
            logger.error(ex);
        }
        return props.getProperty("mirror.path");
    }

    /**
     * Test of checkForFileOrphans method, of class MaintenanceService.
     */
//    @Test
    public void testCheckForFileOrphans() {
        System.out.println("checkForFileOrphans");
        assertTrue(recordingDAO.findAll().isEmpty());
        File[] otrkeys = storageDir.listFiles(new FilenameFilter() {

            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".otrkey");
            }
        });
        assertNotNull(otrkeys);
        assertTrue(otrkeys.length > 0);
        System.out.println("Found these OTRKEYs: " + Arrays.toString(otrkeys));
        Recording rec = new Recording(otrkeys[0].getName());
        rec.setAvailable(true);
        recordingDAO.saveOrUpdate(rec);
        assertSame(1, recordingDAO.findAll().size());

        int files = otrkeys.length;
        Set<File> result = maintenanceService.checkForFileOrphans();
        assertSame("Error in method checkForFileOrphans()", files - 1, result.size());
        assertEquals(files, recordingDAO.findAll().size());
    }

    /**
     * Test of checkForDBOrphans method, of class MaintenanceService.
     */
    @Test
    public void testCheckForDBOrphans() {
        System.out.println("checkForDBOrphans");
        int validRecs = maintenanceService.checkForDBOrphans().size();
        int times = 10;
        for (int i=0; i < times; i++) {
            Recording rec = new Recording("File_" + (i+1) + ".otrkey");
            rec.setAvailable(true);
            recordingDAO.saveOrUpdate(rec);
        }
        assertSame(validRecs + times, recordingDAO.findAll(true).size());
        List<Recording> result = maintenanceService.checkForDBOrphans();
        assertSame(10, result.size());
        assertSame(validRecs, recordingDAO.findAll(true).size());
    }

    /**
     * Test of removeOldRecordings method, of class MaintenanceService.
     */
    @Test
    public void testRemoveOldRecordings() {
        System.out.println("removeOldRecordings");
        int times = 10;
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -31);
        for (int i=0; i < times; i++) {
            Recording rec = new Recording("File_" + (i+1) + ".otrkey");
            rec.setAvailable(true);
            recordingDAO.saveOrUpdate(rec);
        }
        assertSame(times, recordingDAO.findAll(true).size());
        List<Recording> result = maintenanceService.removeOldRecordings();
        assertSame("Recordings are newly created and may never be \"garbaged\"!", 0, result.size());
    }
}
