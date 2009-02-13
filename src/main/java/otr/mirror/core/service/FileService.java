package otr.mirror.core.service;

import java.io.File;

/**
 *
 * @author Marcus Krassmann
 */
public interface FileService {

    /**
     * Gets an OTRKEY for a given filename. If the OTRKEY does not exist, the
     * return value is <tt>null</tt>.
     * 
     * @param filename the filename of the OTRKEY
     * @return the file object
     */
    File getFile(String filename);

    /**
     * Gets all OTRKEYs in the usual storage path.
     *
     * @return all OTRKEYs
     */
    File[] getAllFiles();

    /**
     * Moves a file to the so-called "orphen folder".
     *
     * @param file the OTRKEY file
     * @return <tt>true</tt> if the file was successfully moved to the
     * orphen folder, else <tt>false</tt>
     */
    boolean moveToOrphenFolder(File file);

    boolean delete(String filename);
}
