package otr.mirror.core.model;

/**
 * An enumeration of all possible video formats.
 * 
 * @author Marcus Krassmann
 */
public enum Format {

    DIVX,
    HQ,
    MP4,
    UNKNOWN;

    @Override
    public String toString() {
        switch (this) {
            case DIVX: return "DivX";
            case HQ: return "HQ";
            case MP4: return "mp4";
            default: return "unknown";
        }
    }

    
}
