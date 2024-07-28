package com.mf.url;

import net.jcip.annotations.ThreadSafe;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
public class UrlRepositoryInMem implements UrlRepository {
    private ConcurrentHashMap<String, String> urlToShortUrl = new ConcurrentHashMap();
    private ConcurrentHashMap<String, String> shortUrlToUrl = new ConcurrentHashMap();


    @Override
    public Optional<String> createIfNotExists(String url, String shortUrl) {
        return Optional.ofNullable(urlToShortUrl.computeIfAbsent(url, k -> {
            shortUrlToUrl.put(shortUrl, k);
            return shortUrl;
        }));
    }

    @Override
    public Optional<String> getOriginalUrl(String shortUrl) {
        return Optional.ofNullable(shortUrlToUrl.get(shortUrl));
    }
}
