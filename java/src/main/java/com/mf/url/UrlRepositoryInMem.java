package com.mf.url;

import net.jcip.annotations.ThreadSafe;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;


@ThreadSafe
public class UrlRepositoryInMem implements UrlRepository {
     private int capacity = 10;
     private ConcurrentHashMap<String,String> db = new ConcurrentHashMap();
     private ConcurrentHashMap<String,String> dbRev = new ConcurrentHashMap();

     @Override
     public synchronized Optional<String> createIfNotExists(String url, String shortUrl) {
          db.putIfAbsent(url, shortUrl);
          dbRev.put(db.get(url),url);
          return Optional.of(db.get(url));
     }

     @Override
     public Optional<String> getOriginalUrl(String shortUrl) {
         switch (dbRev.getOrDefault(shortUrl, "Nil") ){
              case "Nil"-> {return Optional.empty();}
              case String url -> {return Optional.of(url);}
         }
     }
}
