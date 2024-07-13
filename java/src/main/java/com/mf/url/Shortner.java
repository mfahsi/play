package com.mf.url;

import java.util.Optional;

public interface Shortner {

     Optional<String> shorten(String url);
     Optional<String> originalUrl(String shortUrl);

}

