package com.mf.url;

import scala.util.Random;

import java.util.Optional;

public class ShortUrlServiceImpl implements ShortUrlService {
    private final UrlRepository repo;
    private final Random gen = new scala.util.Random();


    public ShortUrlServiceImpl(UrlRepository aRepo ) {
        repo = aRepo;
    }

    @Override
    public Optional<String> shorten(String url) {
        if(repo.getOriginalUrl(url).isPresent()){
            return repo.getOriginalUrl(url);
        }else{
            return repo.createIfNotExists(url, pickAshortUrl(url));
        }
    }

    @Override
    public Optional<String> originalUrl(String shortUrl) {
       return repo.getOriginalUrl(shortUrl);
    }

    String pickAshortUrl(String shortUrl) {
        return gen.nextString(8);
    }
}
