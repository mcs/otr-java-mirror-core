package otr.mirror.core.model;

import otr.mirror.core.util.FilenameUtil;

import javax.persistence.*;
import java.util.Date;

/**
 * A recording represents an OTRKEY file with its information.
 * 
 * @author Marcus Krassmann
 */
@Entity
@Table(name = "recordings")
public class Recording extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    private String filename;
    private long filesize;
    private Date startTime;
    private String tvChannel;
    private Format format;
    private Boolean available;
    private Integer downloaded;

    /**
     * Constructs a completely uninitialized recording.
     */
    protected Recording() {
        // default bean constructor
    }

    /**
     * Constructs a fully initialized recording by parsing all relevant
     * infrmation from the filename. The recording will be set to
     * "not available" by default.
     *
     * @param filename the filename of the recording
     */
    public Recording(String filename) {
        this.filename = filename;
        this.startTime = FilenameUtil.getStartDate(filename);
        this.tvChannel = FilenameUtil.getTvChannel(filename);
        this.format = FilenameUtil.getFormat(filename);
        this.available = false;
        this.downloaded = 0;
    }

    /**
     * Returns the filename of the recording.
     * 
     * @return the filename
     */
    @Column(nullable = false, unique = true)
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
     * Gets the date when the recording started.
     * 
     * @return the start date
     */
    @Temporal(TemporalType.TIMESTAMP)
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
     * Gets the date when the recording ended.
     *
     * @return the end date
     */
    @Transient
    public Date getEndTime() {
        return FilenameUtil.getEndDate(filename);
    }

    /**
     * Gets the tv channel of the recording.
     * 
     * @return the tv vhannel
     */
    public String getTvChannel() {
        return tvChannel;
    }

    /**
     * Sets the tv channel of the recording.
     * 
     * @param tvChannel the tv channel
     */
    public void setTvChannel(String tvChannel) {
        this.tvChannel = tvChannel;
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

    /**
     * Gets the format of the recording.
     * 
     * @return the format
     */
    public Format getFormat() {
        return format;
    }

    /**
     * Sets the quality format of the recording.
     * 
     * @param format the quality format
     */
    public void setFormat(Format format) {
        this.format = format;
    }

    /**
     * Gets how many times this file has been downloaded.
     *
     * @return number of downloads
     */
    public Integer getDownloaded() {
        return downloaded;
    }

    /**
     * Sets how many times this file has been downloaded.
     * 
     * @param downloaded number of downloads
     */
    public void setDownloaded(Integer downloaded) {
        this.downloaded = downloaded;
    }

    public void addDownload() {
        if (this.downloaded == null) {
            downloaded = 0;
        }
        downloaded++;
    }

    /**
     * Gets the name of the recording.
     * 
     * @return the name of the recording
     */
    @Transient
    public String getName() {
        return FilenameUtil.getName(filename);
    }

    @Override
    public String toString() {
        return "Recording '" + filename + "'";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Recording)) {
            return false;
        }
        
        final Recording other = (Recording) obj;
        if ((this.filename == null) ? (other.filename != null) : !this.filename.equals(other.filename)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return filename.hashCode();
    }

}
