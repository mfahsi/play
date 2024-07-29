package com.mf.url;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UrlRepositoryTest {

    UrlRepository  repo =  new UrlRepositoryInMem(2);

    @BeforeEach
    void setUp() {
        System.out.println("reset");
        repo = new UrlRepositoryInMem(2);
    }

    @Test
    public void testCreateUrl(){
            assertEquals(repo.createIfNotExists("url1234","1234"), Optional.of("1234"));
            assertEquals(repo.createIfNotExists("url1234","567"), Optional.of("1234"));
    }

    @Test
    public void testGetUrl(){
        assertEquals(repo.createIfNotExists("url1234","1234"), Optional.of("1234"));
        assertEquals(repo.getOriginalUrl("567"), Optional.empty());
        assertEquals(repo.getOriginalUrl("1234"), Optional.of("url1234"));
    }
}
