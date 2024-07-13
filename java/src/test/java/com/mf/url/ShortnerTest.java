package com.mf.url;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ShortnerTest {

    UrlRepository  repo =  new UrlRepositoryInMem();
    ShortUrlService urlShortner =  new ShortUrlServiceImpl(repo);

    @BeforeEach
    void setUp() {
        System.out.println("reset");
        repo = new UrlRepositoryInMem();
        urlShortner =  new ShortUrlServiceImpl(repo);
    }

    @Test
    public void testInvariantGetUnknownShort(){
        assertEquals(urlShortner.originalUrl("never stored"),Optional.empty());
    }

    @Test
    public void testGetUrl(){
        var shorturl = urlShortner.shorten("url1234");
        assertTrue(shorturl.get().length() < 6,"must be max 6");
        assertEquals(urlShortner.originalUrl(shorturl.get()),Optional.of("url1234"));
    }
}
