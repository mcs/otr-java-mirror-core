package otr.mirror.core.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Marcus Krassmann
 */
@Entity
@Table(name = "downloads")
public class Download extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    private Recording recording;
    private String ip;

    protected Download() {
        // for persistance
    }

    public Download(Recording recording, String ip) {
        this.recording = recording;
        this.ip = ip;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @ManyToOne
    public Recording getRecording() {
        return recording;
    }

    public void setRecording(Recording recording) {
        this.recording = recording;
    }
}
