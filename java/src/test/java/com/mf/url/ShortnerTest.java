package com.mf.url;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ShortnerTest {

    UrlRepository  repo =  new UrlRepositoryInMem(2);
    ShortUrlService urlShortner =  new ShortUrlServiceImpl(repo,10);

    @BeforeEach
    void setUp() {
        System.out.println("reset");
        repo = new UrlRepositoryInMem(2);
        urlShortner =  new ShortUrlServiceImpl(repo,10);
    }

    @Test
    public void testInvariantGetUnknownShort(){
        assertEquals(urlShortner.originalUrl("never stored"),Optional.empty());
    }

    @Test
    public void testGetUrl(){
        var shorturl = urlShortner.shorten("url1234");
        System.out.println("short="+ shorturl.get());
        assertTrue(shorturl.get().length() == 8,"must be max 6");
        assertEquals(urlShortner.originalUrl(shorturl.get()),Optional.of("url1234"));
    }

    @Test
    public void testGetUrlFull(){
        repo = new UrlRepositoryInMem(2);
        assertTrue(urlShortner.shorten("url1234").isPresent());
        assertTrue(urlShortner.shorten("url12342").isPresent());
        assertTrue(urlShortner.shorten("url12343").isEmpty());
    }
}
