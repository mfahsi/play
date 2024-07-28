package com.mf.url;

import net.jcip.annotations.ThreadSafe;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


@ThreadSafe
public class UrlRepositoryInMem implements UrlRepository {
    private int capacity = 10;
    private ConcurrentHashMap<String, String> db = new ConcurrentHashMap();
    private ConcurrentHashMap<String, String> dbRev = new ConcurrentHashMap();

    private AtomicInteger urlCount = new AtomicInteger(100);

    @Override
    public synchronized Optional<String> createIfNotExists(String url, String shortUrl) {
        if (db.containsKey(url)) {
            return Optional.of(db.get(url));
        }else{
            db.put(url,shortUrl);
            dbRev.put(shortUrl, url);
            urlCount.incrementAndGet();
            return Optional.of(db.get(url));
        }
    }

    @Override
    public Optional<String> getOriginalUrl(String shortUrl) {
        switch (dbRev.getOrDefault(shortUrl, "Nil")) {
            case "Nil" -> {
                return Optional.empty();
            }
            case String url -> {
                return Optional.of(url);
            }
        }
    }
}
