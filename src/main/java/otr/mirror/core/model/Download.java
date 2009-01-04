package otr.mirror.core.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * A single, allowed download.
 * 
 * @author Marcus Krassmann
 */
@Entity
@Table(name = "downloads", uniqueConstraints = @UniqueConstraint(columnNames = "dlKey"))
public class Download extends AbstractEntity {

    private static final long serialVersionUID = 1L;
    
    private String dlKey;
    private Recording recording;

    public String getDlKey() {
        return dlKey;
    }

    public void setDlKey(String dlKey) {
        this.dlKey = dlKey;
    }

    @ManyToOne
    public Recording getRecording() {
        return recording;
    }

    public void setRecording(Recording recording) {
        this.recording = recording;
    }

}
