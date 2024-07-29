package com.mf.url;

import net.jcip.annotations.ThreadSafe;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
public class UrlRepositoryInMem implements UrlRepository {
    private final ConcurrentHashMap<String, String> urlToShortUrl = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, String> shortUrlToUrl = new ConcurrentHashMap<>();
    private final int maxUrls;

    public UrlRepositoryInMem(int maxUrls) {
        this.maxUrls = maxUrls;
    }

    @Override
    public synchronized Optional<String> createIfNotExists(String url, String shortUrl) {
        if (urlToShortUrl.size() >= maxUrls) {
            return Optional.empty(); // Don't insert if the limit is reached
        }
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
