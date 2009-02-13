package otr.mirror.core.dao;

import otr.mirror.core.model.User;

/**
 * Defines DAO operations for User objects.
 * @author Marcus Krassmann
 */
public interface UserDAO extends AbstractDAO<User> {

    User findByLogin(String login);

    User findByEmail(String email);
}
