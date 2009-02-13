package otr.mirror.core.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * A business object is anything that is important for the business model.
 * 
 * @author Marcus Krassmann
 */
@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

    private Long id;
    private Date creationDate;
    private Date modifiedDate;

    public AbstractEntity() {
    }

    @GenericGenerator(name = "hbm-increment", strategy = "increment")
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "hbm-increment")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Transient
    public final Class<?> getProbablyRealClass() {
        return this.getClass();
    }

    /**
     * This helper method should return the real class of the object.
     * <p>
     * It may happen that the type of the class is hidden by a proxy. This
     * method should always return the real type. This can be done by returning
     * something like RealClass.class
     * 
     * @return the real class of the object
     */
//    public abstract Class<? extends BusinessObject> clazz();

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AbstractEntity)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        final AbstractEntity other = (AbstractEntity) obj;
        return new EqualsBuilder()
                .append(id, other.id)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "Id: " + id;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreationDate() {
        return creationDate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @PrePersist
    public void setCreationDate() {
        if (creationDate == null) {
            this.creationDate = new Date();
        }
        this.modifiedDate = creationDate;
    }

    @PreUpdate
    public void setModifiedDate() {
        this.modifiedDate = new Date();
    }

}
