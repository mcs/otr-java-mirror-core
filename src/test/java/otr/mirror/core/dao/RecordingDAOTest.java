package otr.mirror.core.dao;

import java.util.List;
import otr.mirror.core.OtrTests;
import otr.mirror.core.model.Recording;
import otr.mirror.core.util.FilenameUtilTest;

/**
 * Tests for RecordingDAO.
 * 
 * @author Marcus Krassmann
 */
public class RecordingDAOTest extends OtrTests {

    private RecordingDAO recordingDAO;

    public RecordingDAOTest(String testName) {
        super(testName);
    }

    public void setRecordingDAO(RecordingDAO recordingDAO) {
        this.recordingDAO = recordingDAO;
    }

    @Override
    public void onSetUpInTransaction() throws Exception {
        // create three different recordings, last one not available
        String [] files = FilenameUtilTest.files;
        for (int i = 0; i < files.length; i++) {
            Recording rec = new Recording();
            rec.setFilename(files[i]);
            rec.setAvailable(i == files.length - 1);
            recordingDAO.saveOrUpdate(rec);
        }
    }

    /**
     * Test of findAllByCreationDate method, of class RecordingDAO.
     */
    public void testFindAllByCreationDate() {
        System.out.println("findAllByCreationDate");
        List<Recording> result = recordingDAO.findAllByCreationDate();
        String[] files = FilenameUtilTest.files;
        assertSame(files.length, result.size());
        System.out.println(result);
        for (int i = 0; i < files.length; i++) {
            assertEquals(files[files.length-i-1], result.get(i).getFilename());
        }

    }
}
