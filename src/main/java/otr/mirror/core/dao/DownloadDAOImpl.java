package otr.mirror.core.dao;

import java.util.List;
import otr.mirror.core.model.Download;

/**
 *
 * @author marcus
 */
public class DownloadDAOImpl extends GenericDAOImpl<Download> implements DownloadDAO {

    @Override
    public Download findByKey(String downloadKey) {
        List<Download> keys = getJpaTemplate().find("FROM Download WHERE dlKey = ?", downloadKey);
        return keys.size() == 1 ? keys.get(0) : null;
    }

}
