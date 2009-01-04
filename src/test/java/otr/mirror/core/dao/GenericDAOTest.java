package otr.mirror.core.dao;

import java.util.List;
import otr.mirror.core.OtrTests;
import otr.mirror.core.model.Recording;

/**
 *
 * @author Marcus Krassmann
 */
public class GenericDAOTest extends OtrTests {

    private RecordingDAO recordingDAO;
    private Recording persistentEntity;

    public GenericDAOTest(String testName) {
        super(testName);

    }

    public void setRecordingDAO(RecordingDAO recordingDAO) {
        this.recordingDAO = recordingDAO;
    }

    @Override
    protected void onSetUpInTransaction() throws Exception {
        Recording me = new Recording();
        me.setFilename("MyName");
        recordingDAO.saveOrUpdate(me);
        persistentEntity = me;
    }

    /**
     * Test of saveOrUpdate method, of class GenericDAO.
     */
    public void testSaveOrUpdate() {
        System.out.println("saveOrUpdate");
        Recording me = new Recording();
        assertNull(me.getId());
        recordingDAO.saveOrUpdate(me);
        assertNotNull(me.getId());
    }

    /**
     * Test of delete method, of class GenericDAO.
     */
    public void testDelete() {
        System.out.println("delete");
        Recording me = recordingDAO.findById(persistentEntity.getId());
        assertNotNull(me);

        recordingDAO.delete(persistentEntity);
        me = recordingDAO.findById(persistentEntity.getId());
        assertNull(me);
    }

    /**
     * Test of findById method, of class GenericDAO.
     */
    public void testFindById() {
        System.out.println("findById");
        Long id = persistentEntity.getId();
        Recording result = recordingDAO.findById(id);
        assertEquals(persistentEntity, result);

        result = recordingDAO.findById(-1);
        assertNull(result);
    }

    /**
     * Test of findAll method, of class GenericDAO.
     */
    public void testFindAll() {
        System.out.println("findAll");
        recordingDAO.saveOrUpdate(new Recording());
        List<Recording> result = recordingDAO.findAll();
        assertEquals(2, result.size());
        assertNotSame(result.get(0).getFilename(), result.get(1).getFilename());
    }

    /**
     * Test of findByExample method, of class GenericDAO.
     */
    public void testFindByExample() {
        System.out.println("findByExample");
        Recording entity = new Recording();
        entity.setFilename("MyName");
        List<Recording> result = recordingDAO.findByExample(entity);
        assertEquals(1, result.size());
        assertEquals(persistentEntity, result.get(0));
    }

    /**
     * Test of findUniqueByExample method, of class GenericDAO.
     */
    public void testFindUniqueByExample() {
        System.out.println("findUniqueByExample");
        Recording entity = new Recording();
        entity.setFilename("MyName");
        Recording result = recordingDAO.findUniqueByExample(entity);
        assertEquals(persistentEntity, result);

        entity = new Recording();
        entity.setFilename("AnotherName");
        result = recordingDAO.findUniqueByExample(entity);
        assertNull(result);
    }
}
