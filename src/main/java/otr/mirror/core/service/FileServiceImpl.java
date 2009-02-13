package otr.mirror.core.service;

import java.io.File;
import java.io.FilenameFilter;

/**
 *
 * @author Marcus Krassmann
 */
public class FileServiceImpl implements FileService {

    private String storagePath;

    public void setStoragePath(String storagePath) {
        this.storagePath = storagePath;
    }

    @Override
    public File getFile(String filename) {
        File file = new File(storagePath, filename);
        return file.exists() ? file : null;
    }

    @Override
    public File[] getAllFiles() {
        File file = new File(storagePath);
        File[] otrkeys = file.listFiles(new FilenameFilter() {

            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".otrkey");
            }
        });
        return otrkeys;
    }

    @Override
    public boolean moveToOrphenFolder(File file) {
        // pre-conditions
        if (file == null || !file.exists()) {
            return false;
        }

        // try to move the file
        File orphenDir = new File(storagePath, "orphens");
        orphenDir.mkdir();
        File orphenTarget = new File(orphenDir, file.getName());
        return file.renameTo(orphenTarget);
    }

    @Override
    public boolean delete(String filename) {
        File file = new File(storagePath, filename);
        return file.delete();
    }
}
