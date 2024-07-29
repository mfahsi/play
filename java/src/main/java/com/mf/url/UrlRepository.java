package com.mf.url;

import java.util.Optional;

public interface UrlRepository {
    /**
     * @param url
     * @param shortUrl
     * @return false if exists already (duplicate)
     * false if other failures
     */
    Optional<String> createIfNotExists(String url, String shortUrl);

    Optional<String> getOriginalUrl(String shortUrl);


}

