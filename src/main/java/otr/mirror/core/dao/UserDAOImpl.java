package otr.mirror.core.dao;

import otr.mirror.core.model.User;

import javax.persistence.NoResultException;

/**
 *
 * @author Marcus Krassmann
 */
public class UserDAOImpl extends AbstractDAOImpl<User> implements UserDAO {

    @Override
    public User findByLogin(String login) {
        try {
            return (User) em.createQuery("FROM User WHERE login = :login").setParameter("login", login).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public User findByEmail(String email) {
        try {
            return (User) em.createQuery("FROM User WHERE email = :email").setParameter("email", email).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

}
