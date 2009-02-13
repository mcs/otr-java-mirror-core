package otr.mirror.core.dao;

import otr.mirror.core.model.Download;

import javax.persistence.Query;
import java.util.List;

/**
 *
 * @author Marcus Krassmann
 */
public class DownloadDAOImpl extends AbstractDAOImpl<Download> implements DownloadDAO {

    @Override
    @SuppressWarnings("unchecked")
    public List<Download> findByIp(String ip) {
        Query qry = em.createQuery("FROM Download WHERE ip = :ip");
        qry.setParameter("ip", ip);
        return qry.getResultList();
    }

    
}
