package otr.mirror.core.dao;

import static org.junit.Assert.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import otr.mirror.core.OtrTests;
import otr.mirror.core.model.Recording;
import otr.mirror.core.util.FilenameUtilTest;

import java.util.Calendar;
import java.util.List;

/**
 * Tests for RecordingDAO.
 * 
 * @author Marcus Krassmann
 */
public class RecordingDAOTest extends OtrTests {

    @Autowired private RecordingDAO recordingDAO;

    /**
     * Test of findAllByCreationDate method, of class RecordingDAO.
     */
    @Test
    public void testFindAllByCreationDate() {
        System.out.println("findAllByCreationDate");
        // create three different recordings
        String [] files = FilenameUtilTest.files;
        for (int i = 0; i < files.length; i++) {
            Recording rec = new Recording(files[i]);
            rec.setAvailable(true);
            recordingDAO.saveOrUpdate(rec);
        }
        List<Recording> result = recordingDAO.findAllByCreationDate();
        assertSame(files.length < 3 ? files.length : 3, result.size());
        System.out.println(result);
        for (int i = 0; i < files.length; i++) {
            assertEquals(files[files.length-i-1], result.get(i).getFilename());
        }

    }

    @Test
    public void testFindByFilename() {
        System.out.println("findByFilename");
        // create three different recordings
        String [] files = FilenameUtilTest.files;
        for (int i = 0; i < files.length; i++) {
            Recording rec = new Recording(files[i]);
            rec.setAvailable(true);
            recordingDAO.saveOrUpdate(rec);
        }
        Recording result = recordingDAO.findByFilename(FilenameUtilTest.files[0]);
        assertNotNull(result);
    }

    /**
     * Test of findAll method, of class RecordingDAO.
     */
    @Test
    public void testFindAll() {
        System.out.println("findAll");
        System.out.println("true = " + recordingDAO.findAll(true));
        System.out.println("false = " + recordingDAO.findAll(false));
        int available = 5;
        int notAvailable = 3;
        for (int i=0; i<available; i++) {
            Recording rec = new Recording("File_" + i + ".otrkey");
            rec.setAvailable(true);
            recordingDAO.saveOrUpdate(rec);
        }
        for (int i=0; i<notAvailable; i++) {
            Recording rec = new Recording("File_" + (available + i) + ".otrkey");
            rec.setAvailable(false);
            recordingDAO.saveOrUpdate(rec);
        }
        List<Recording> result = recordingDAO.findAll(true);
        assertSame("Result: " + result, available, recordingDAO.findAll(true).size());
        assertSame(notAvailable, recordingDAO.findAll(false).size());
    }

    /**
     * Test of findModifiedOlderThan method, of class RecordingDAO.
     */
    @Test
    public void testFindModifiedOlderThan() throws InterruptedException {
        System.out.println("findModifiedOlderThan");
        assertTrue(recordingDAO.findAll().isEmpty());
        Calendar cal = null;
        int times = 10;
        for (int i=0; i < times; i++) {
            Recording rec = new Recording("File_" + (i+1) + ".otrkey");
            rec.setAvailable(true);
            recordingDAO.saveOrUpdate(rec);
            System.out.println("Rec has modifiedDate " + rec.getModifiedDate());
            if (i == 6 - 1) {
                Thread.sleep(100);
                cal = Calendar.getInstance();
                System.out.println("Calendar has date " + cal.getTime());
                Thread.sleep(100);
            }
        }
        for (Recording each : recordingDAO.findAll()) {
            System.out.println("modified on " + each.getModifiedDate());
        }
        assertSame(times, recordingDAO.findAll(true).size());
        assertSame(6, recordingDAO.findModifiedOlderThan(cal.getTime()).size());
    }
}
