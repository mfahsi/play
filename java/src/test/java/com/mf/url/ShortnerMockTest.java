package com.mf.url;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ShortnerMockTest {

    UrlRepository  repo =  new UrlRepositoryInMem(2);
    ShortUrlService urlShortner =  new ShortUrlServiceImpl(repo,10);

    @BeforeEach
    void setUp() {
        System.out.println("reset");
        repo = Mockito.mock(UrlRepository.class);
        urlShortner =  new ShortUrlServiceImpl(repo,10);
    }

    @Test
    public void testInvariantGetUnknownShort(){
        Mockito.when(repo.getOriginalUrl("never stored")).thenReturn(Optional.empty());
        assertEquals(urlShortner.originalUrl("never stored"),Optional.empty());
    }

    @Test
    public void testGetUrl(){
        Mockito.when(repo.createIfNotExists(Mockito.eq("url123"),Mockito.anyString())).thenReturn(Optional.of("http://xyz"));
        Mockito.when(repo.getOriginalUrl("url123")).thenReturn(Optional.empty());
        assertEquals(urlShortner.shorten("url123"),Optional.of("http://xyz"));
    }
}
