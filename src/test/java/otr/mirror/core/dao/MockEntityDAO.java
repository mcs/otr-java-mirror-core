package otr.mirror.core.dao;

import otr.mirror.core.model.MockEntity;


/**
 * A helper DAO for testing the GenericDAO interface.
 *
 * @author Marcus Krassmann
 */
public interface MockEntityDAO extends GenericDAO<MockEntity> {
    // no new methods needed, just terminate the generic type
}
