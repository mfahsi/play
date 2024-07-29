package com.mf.url;

import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class MaxCapacityExceeded extends Exception{

}
class UrlShortner {
    final Integer maxUris;
    final AtomicInteger counter= new AtomicInteger(1);
    final ConcurrentHashMap<URI,URI> repo = new ConcurrentHashMap<URI,URI>();

    public UrlShortner(Integer aMaxUris){
        maxUris = aMaxUris;
    }

    public URI random(){
        Integer unique = counter.incrementAndGet();
        URI random = URI.create("https://www.rev.me"+unique);
        return random;
    }

    public URI shortUrlFrom(URI uri) throws MaxCapacityExceeded {
        if(uri == null){
            throw new NullPointerException("uri must be provided");
        }
        synchronized (maxUris) {
            if (repo.keySet().stream().count() >= maxUris) {
                throw new MaxCapacityExceeded();
            }
            var shortUri = random();
            repo.put(shortUri, uri);
            return shortUri;
        }
    }

    public Optional<URI> getOriginal(URI uri){
        if(uri == null){
            throw new NullPointerException("uri must be provided");
        }
        return Optional.ofNullable(repo.get(uri));
    }
}

class InterviewTest {


    @Test
    public final void test_short_url_and_retreive() throws MaxCapacityExceeded {
        UrlShortner service = new UrlShortner(2);
        URI aURI = URI.create("wwww.helo123456678585");
        URI shorURI = service.shortUrlFrom(aURI);
        assertTrue(shorURI.getRawPath().length() <= 6);
        assertEquals(service.getOriginal(shorURI),Optional.of(aURI));
    }

    @Test
    public final void test_max_capacity() throws Exception {
        UrlShortner service = new UrlShortner(1);
        URI aURI = URI.create("wwww.helo123456678585");
        service.shortUrlFrom(aURI);
        assertThrows(MaxCapacityExceeded.class, () -> service.shortUrlFrom(aURI));
    }

    @Test
    public final void test_get_unknown() throws Exception {
        UrlShortner service = new UrlShortner(2);
        assertEquals(Optional.empty(),service.getOriginal(URI.create("unknown")));
    }
    @Test
    public final void test_null_values() throws Exception {
        UrlShortner service = new UrlShortner(2);
        assertThrows(NullPointerException.class, () ->service.getOriginal(null));
        assertThrows(NullPointerException.class, ()->service.shortUrlFrom(null));
    }
}
