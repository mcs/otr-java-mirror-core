package otr.mirror.core.dao;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import otr.mirror.core.OtrTests;
import otr.mirror.core.model.Recording;

import java.util.List;

/**
 *
 * @author Marcus Krassmann
 */
public class GenericDAOTest extends OtrTests {

    @Autowired private RecordingDAO recordingDAO;
    private Recording persistentEntity;

    @Before
    public void onSetUpInTransaction() throws Exception {
        Recording me = new Recording("MyName");
        recordingDAO.saveOrUpdate(me);
        persistentEntity = me;
    }

    /**
     * Test of saveOrUpdate method, of class GenericDAO.
     */
    @Test
    public void testSaveOrUpdate() {
        System.out.println("saveOrUpdate");
        Recording me = new Recording("testname");
        assertNull(me.getId());
        recordingDAO.saveOrUpdate(me);
        assertNotNull(me.getId());
    }

    /**
     * Test of delete method, of class GenericDAO.
     */
    @Test
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
    @Test
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
    @Test
    public void testFindAll() {
        System.out.println("findAll");
        Recording rec = new Recording("lala");
        recordingDAO.saveOrUpdate(rec);
        List<Recording> result = recordingDAO.findAll();
        assertEquals(2, result.size());
        assertNotSame(result.get(0).getFilename(), result.get(1).getFilename());
    }

    /**
     * Test of findByExample method, of class GenericDAO.
     */
    @Test
    public void testFindByExample() {
        System.out.println("findByExample");
        Recording entity = new Recording("MyName");
        List<Recording> result = recordingDAO.findByExample(entity);
        assertEquals(1, result.size());
        assertEquals(persistentEntity, result.get(0));
    }

    /**
     * Test of findUniqueByExample method, of class GenericDAO.
     */
    @Test
    public void testFindUniqueByExample() {
        System.out.println("findUniqueByExample");
        Recording entity = new Recording("MyName");
        Recording result = recordingDAO.findUniqueByExample(entity);
        assertEquals(persistentEntity, result);

        entity = new Recording("AnotherName");
        result = recordingDAO.findUniqueByExample(entity);
        assertNull(result);
    }
}
