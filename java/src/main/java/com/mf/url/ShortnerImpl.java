package com.mf.url;

import scala.collection.immutable.Range;

import java.util.Optional;
import java.util.random.RandomGenerator;

import static java.lang.StringTemplate.of;

public class ShortnerImpl implements Shortner {
    private final UrlRepository repo;


    public static String generate() {
        var gen = new scala.util.Random();
        return gen.nextString(8);
    }

    public ShortnerImpl(UrlRepository aRepo ) {
        repo = aRepo;
    }

    @Override
    public Optional<String> shorten(String url) {
        if(repo.getOriginalUrl(url).isPresent()){
            return repo.getOriginalUrl(url);
        }else{
            return repo.createIfNotExists(url,compress(url));
        }
    }

    @Override
    public Optional<String> originalUrl(String shortUrl) {
       return repo.getOriginalUrl(shortUrl);
    }

    String compress(String shortUrl) {
        return generate();
    }
}
