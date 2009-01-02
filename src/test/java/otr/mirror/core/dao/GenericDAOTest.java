package otr.mirror.core.dao;

import java.util.List;
import otr.mirror.core.OtrTests;
import otr.mirror.core.model.MockEntity;

/**
 *
 * @author Marcus Krassmann
 */
public class GenericDAOTest extends OtrTests {

    private MockEntityDAO mockEntityDAO;
    private MockEntity persistentEntity;

    public GenericDAOTest(String testName) {
        super(testName);

    }

    public void setMockEntityDAO(MockEntityDAO mockEntityDAO) {
        this.mockEntityDAO = mockEntityDAO;
    }

    @Override
    protected void onSetUpInTransaction() throws Exception {
        MockEntity me = new MockEntity();
        me.setSomeInt(42);
        mockEntityDAO.saveOrUpdate(me);
        persistentEntity = me;
    }

    /**
     * Test of saveOrUpdate method, of class GenericDAO.
     */
    public void testSaveOrUpdate() {
        System.out.println("saveOrUpdate");
        MockEntity me = new MockEntity();
        assertNull(me.getId());
        mockEntityDAO.saveOrUpdate(me);
        assertNotNull(me.getId());
    }

    /**
     * Test of delete method, of class GenericDAO.
     */
    public void testDelete() {
        System.out.println("delete");
        MockEntity me = mockEntityDAO.findById(persistentEntity.getId());
        assertNotNull(me);

        mockEntityDAO.delete(persistentEntity);
        me = mockEntityDAO.findById(persistentEntity.getId());
        assertNull(me);
    }

    /**
     * Test of findById method, of class GenericDAO.
     */
    public void testFindById() {
        System.out.println("findById");
        Long id = persistentEntity.getId();
        MockEntity result = mockEntityDAO.findById(id);
        assertEquals(persistentEntity, result);

        result = mockEntityDAO.findById(-1);
        assertNull(result);
    }

    /**
     * Test of findAll method, of class GenericDAO.
     */
    public void testFindAll() {
        System.out.println("findAll");
        mockEntityDAO.saveOrUpdate(new MockEntity());
        List<MockEntity> result = mockEntityDAO.findAll();
        assertEquals(2, result.size());
        assertNotSame(result.get(0).getSomeInt(), result.get(1).getSomeInt());
    }

    /**
     * Test of findByExample method, of class GenericDAO.
     */
    public void testFindByExample() {
        System.out.println("findByExample");
        MockEntity entity = new MockEntity();
        entity.setSomeInt(42);
        List<MockEntity> result = mockEntityDAO.findByExample(entity);
        assertEquals(1, result.size());
        assertEquals(persistentEntity, result.get(0));
    }

    /**
     * Test of findUniqueByExample method, of class GenericDAO.
     */
    public void testFindUniqueByExample() {
        System.out.println("findUniqueByExample");
        MockEntity entity = new MockEntity();
        entity.setSomeInt(42);
        MockEntity result = mockEntityDAO.findUniqueByExample(entity);
        assertEquals(persistentEntity, result);

        entity = new MockEntity();
        entity.setSomeInt(666);
        result = mockEntityDAO.findUniqueByExample(entity);
        assertNull(result);
    }
}
