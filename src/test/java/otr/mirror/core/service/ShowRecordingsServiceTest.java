package otr.mirror.core.service;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import otr.mirror.core.OtrTests;
import otr.mirror.core.dao.RecordingDAO;
import otr.mirror.core.model.Recording;
import otr.mirror.core.util.FilenameUtilTest;

import java.util.List;

/**
 *
 * @author Marcus Krassmann
 */
public class ShowRecordingsServiceTest extends OtrTests {

    @Autowired private ShowRecordingsService showRecordingsService;
    @Autowired private RecordingDAO recordingDAO;

    @Before
    public void onSetUpInTransaction() throws Exception {
        for (String filename : FilenameUtilTest.files) {
            Recording rec = new Recording(filename);
            rec.setAvailable(true);
            recordingDAO.saveOrUpdate(rec);
        }
    }

    /**
     * Test of getRecordings method, of class ShowRecordingsService.
     */
    @Test
    public void testGetRecordings() {
        System.out.println("getRecordings");
        List<Recording> result = showRecordingsService.getRecordings();
        assertSame(FilenameUtilTest.files.length, result.size());
        for (int i = 0; i < FilenameUtilTest.files.length - 1; i++) {
            System.out.println("*** " + result.get(i) + " => " + result.get(i+1));
            System.out.println("*** " + result.get(i).getStartTime() + " is after " + result.get(i+1).getStartTime());
            // recording x has been sent same time or after recording x+1
            assertTrue(result.get(i).getStartTime().equals(result.get(i+1).getStartTime()) || result.get(i).getStartTime().after(result.get(i+1).getStartTime()));
        }
    }
}
