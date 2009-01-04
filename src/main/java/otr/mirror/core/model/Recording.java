package otr.mirror.core.model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import otr.mirror.core.util.FilenameUtil;

/**
 * A recording represents an OTRKEY file with its information.
 * 
 * @author Marcus Krassmann
 */
@Entity
@Table(name = "recordings", uniqueConstraints = @UniqueConstraint(columnNames = "filename"))
public class Recording extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    private String filename;
    private long filesize;
    private Date startTime;
    private Boolean available;

    public Recording() {
        // default bean constructor
    }

    public Recording(String filename) {
        this.filename = filename;
        this.startTime = FilenameUtil.getStartDate(filename);
    }

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
     * Gets the size of the file.
     * 
     * @return the filesize in bytes
     */
    public long getFilesize() {
        return filesize;
    }

    /**
     * Sets the size of the file.
     *
     * @param filesize the size of the file in bytes
     */
    public void setFilesize(long filesize) {
        this.filesize = filesize;
    }

    /**
     * Gets the date when the recording was sent.
     * 
     * @return sent date
     */
    @Temporal(TemporalType.DATE)
    public Date getStartTime() {
        return startTime;
    }

    /**
     * Sets the date when the recording was sent.
     *
     * @param startTime the sent date
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
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

    @Transient
    public Format getFormat() {
        return FilenameUtil.getFormat(filename);
    }

    @Override
    public String toString() {
        return "Recording with filename '" + filename + "'";
    }

}
