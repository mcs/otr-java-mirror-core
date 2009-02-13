package otr.mirror.core.service;

import otr.mirror.core.model.User;

/**
 * Service for login users.
 * @author Marcus Krassmann
 */
public interface LoginService {

    public User login(String login, String password);

}
