package otr.mirror.core.service;

import otr.mirror.core.OtrTests;
import java.util.List;
import otr.mirror.core.dao.RecordingDAO;
import otr.mirror.core.model.Recording;
import otr.mirror.core.util.FilenameUtilTest;

/**
 *
 * @author Marcus Krassmann
 */
public class ShowRecordingsServiceTest extends OtrTests {

    private ShowRecordingsService showRecordingsService;
    private RecordingDAO recordingDAO;

    public void setShowRecordingsService(ShowRecordingsService showRecordingsService) {
        this.showRecordingsService = showRecordingsService;
    }

    public void setRecordingDAO(RecordingDAO recordingDAO) {
        this.recordingDAO = recordingDAO;
    }

    public ShowRecordingsServiceTest(String testName) {
        super(testName);
    }

    @Override
    protected void onSetUpInTransaction() throws Exception {
        for (String filename : FilenameUtilTest.files) {
            Recording rec = new Recording();
            rec.setFilename(filename);
            rec.setAvailable(true);
            recordingDAO.saveOrUpdate(rec);
        }
    }

    /**
     * Test of getRecordings method, of class ShowRecordingsService.
     */
    public void testGetRecordings() {
        System.out.println("getRecordings");
        List<Recording> result = showRecordingsService.getRecordings();
        assertSame(FilenameUtilTest.files.length, result.size());
        for (int i = 0; i < FilenameUtilTest.files.length - 1; i++) {
            System.out.println("*** " + result.get(i).getCreationDate() + " is after " + result.get(i+1).getCreationDate());
            assertTrue(result.get(i).getCreationDate().after(result.get(i+1).getCreationDate()));
        }
    }
}
