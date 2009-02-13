package otr.mirror.core.model;

import javax.persistence.*;

/**
 * A simple user.
 * @author Marcus Krassmann
 */
@Entity
@Table(name = "users", uniqueConstraints = {
    @UniqueConstraint(columnNames = "login"),
    @UniqueConstraint(columnNames = "email")
})
public class User extends AbstractEntity {

    private static final long serialVersionUID = 1L;
    private String login;
    private String password;
    private String email;
    private ACCESS access;

    public static enum ACCESS {

        ADMIN, DONATOR, USER;
    }

    public User() {
    }

    public User(String login, String password, String email) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.access = ACCESS.USER;
    }

    @Column(nullable = false)
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Column(nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(nullable = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public ACCESS getAccess() {
        return access;
    }

    public void setAccess(ACCESS access) {
        this.access = access;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (this.login == null ? other.login != null : !this.login.equals(other.login)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (this.login != null ? this.login.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return super.toString() + ", Login: " + login;
    }
}
