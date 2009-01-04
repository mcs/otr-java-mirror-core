package otr.mirror.core.dao;

import otr.mirror.core.model.Download;

/**
 *
 * @author marcus
 */
public interface DownloadDAO extends GenericDAO<Download> {

    Download findByKey(String downloadKey);
}
