package otr.mirror.core.dao;

import otr.mirror.core.model.Recording;


/**
 * A helper DAO for testing the GenericDAO interface.
 *
 * @author Marcus Krassmann
 */
public interface MockEntityDAO extends AbstractDAO<Recording> {
    // no new methods needed, just terminate the generic type
}
