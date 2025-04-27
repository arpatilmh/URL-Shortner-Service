package com.arpatilmh.repository;


import java.util.Map;

public interface UrlRepository {
    void save(UrlEntity urlEntity);
    UrlEntity findByShortUrl(String shortUrl);
    boolean exists(String shortUrl);
    UrlEntity findByLongUrl(String longUrl);
    void incrementVisits(String shortUrl);
    Map<String, Long> getAllVisits();
}
