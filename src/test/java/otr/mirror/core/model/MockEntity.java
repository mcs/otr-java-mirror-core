package otr.mirror.core.model;

import javax.persistence.Entity;

/**
 * A helper entity for allowing unit tests of the GenericDAO interface.
 *
 * @author Marcus Krassmann
 */
@Entity
public class MockEntity extends AbstractEntity {

    private int someInt;

    public int getSomeInt() {
        return someInt;
    }

    public void setSomeInt(int someInt) {
        this.someInt = someInt;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MockEntity)) {
            return false;
        }

        final MockEntity other = (MockEntity) obj;
        return this.someInt == other.someInt;
    }

    @Override
    public int hashCode() {
        return this.someInt;
    }
}
