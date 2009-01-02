package otr.mirror.core.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * A recording represents an OTRKEY file with its information.
 * 
 * @author Marcus Krassmann
 */
@Entity
@Table(name = "recordings", uniqueConstraints = @UniqueConstraint(columnNames = "filename"))
public class Recording extends AbstractEntity {

    private String filename;
    private Boolean available;

    /**
     * Returns the filename of the recording.
     * 
     * @return the filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * Sets the filename of the recording.
     *
     * @param filename the filename of the recording
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * Can the recording be downloaded from this mirror?
     *
     * @return <tt>true</tt> if the recording is available, else <tt>false</tt>
     */
    public Boolean isAvailable() {
        return available;
    }

    /**
     * Set if the recording can be downloaded from this mirror.
     *
     * @param available <tt>true</tt> if the file is available, else <tt>false</tt>
     */
    public void setAvailable(Boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Recording with filename '" + filename + "'";
    }

}
