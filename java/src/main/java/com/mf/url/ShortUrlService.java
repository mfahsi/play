package com.mf.url;

import java.util.Optional;

public interface ShortUrlService {

     Optional<String> shorten(String url);
     Optional<String> originalUrl(String shortUrl);

}

