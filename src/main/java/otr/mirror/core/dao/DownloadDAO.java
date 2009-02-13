package otr.mirror.core.dao;

import otr.mirror.core.model.Download;

import java.util.List;

/**
 *
 * @author Marcus Krassmann
 */
public interface DownloadDAO extends AbstractDAO<Download> {

    List<Download> findByIp(String ip);
}
