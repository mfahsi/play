package com.mf.url;

import scala.util.Random;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Optional;
import java.util.random.RandomGenerator;
import java.util.stream.Collectors;

public class ShortUrlServiceImpl implements ShortUrlService {
    private final UrlRepository repo;
    private final Random gen = new scala.util.Random();
    private final Integer MAX_URLS;


    public ShortUrlServiceImpl(UrlRepository aRepo, Integer maxUrls) {
        repo = aRepo;
        MAX_URLS = maxUrls;
    }

    @Override
    public Optional<String> shorten(String url) {
        if(repo.getOriginalUrl(url).isPresent()){
            return repo.getOriginalUrl(url);
        }else{
            return repo.createIfNotExists(url, pickAshortUrl());
        }
    }

    @Override
    public Optional<String> originalUrl(String shortUrl) {
       return repo.getOriginalUrl(shortUrl);
    }

    private String pickAshortUrl() {
        // Define the character set for the short URL
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        return RandomGenerator.getDefault().ints(8, 0, characters.length())
                .mapToObj(characters::charAt)
                .map(Object::toString)
                .collect(Collectors.joining());
    }
}
