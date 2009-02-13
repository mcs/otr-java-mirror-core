package otr.mirror.core.service;

import org.springframework.util.Assert;
import otr.mirror.core.dao.UserDAO;
import otr.mirror.core.model.User;

/**
 *
 * @author Marcus Krassmann
 */
public class LoginServiceImpl implements LoginService {

    private UserDAO userDAO;

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public User login(String login, String password) {
        Assert.notNull(login);
        Assert.notNull(password);
        
        User user = userDAO.findByLogin(login);
        if (user == null) {
            return null;
        }
        return user.getPassword().equals(password) ? user : null;
    }
}
