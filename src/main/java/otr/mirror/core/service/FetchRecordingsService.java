package otr.mirror.core.service;

/**
 * A service for fetching recordings from external sources.
 * 
 * @author Marcus Krassmann
 */
public interface FetchRecordingsService {

    /**
     * Fetches an OTRKEY file from the given url.
     * <p>
     * After having fetched the file, its recording entity will be changed to
     * "
     * @param url the url where to download the OTRKEY file
     */
    void fetch(String url);

}
