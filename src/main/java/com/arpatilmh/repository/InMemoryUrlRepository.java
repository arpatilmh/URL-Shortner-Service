package com.arpatilmh.repository;

import com.arpatilmh.service.UrlDto;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
@Profile("memory")
public class InMemoryUrlRepository implements UrlRepository {

    private final Map<String, UrlEntity> allUrls = new HashMap<>();

    @Override
    public void save(UrlEntity urlEntity) {
        allUrls.put(urlEntity.getShortUrl(), urlEntity);
    }

    @Override
    public UrlEntity findByShortUrl(String shortUrl) {
        return allUrls.getOrDefault(shortUrl, null);
    }

    @Override
    public boolean exists(String shortUrl) {
        return allUrls.containsKey(shortUrl);
    }

    @Override
    public UrlEntity findByLongUrl(String longUrl) {
        for(UrlEntity urlEntity : allUrls.values()) {
            if (longUrl.equals(urlEntity.getLongUrl())) {
                return urlEntity;
            }
        }

        return null;
    }

    @Override
    public void incrementVisits(String shortUrl) {
        UrlEntity urlEntity = findByShortUrl(shortUrl);
        urlEntity.setVisits(urlEntity.getVisits() + 1L);
        allUrls.put(shortUrl, urlEntity);
    }

    @Override
    public Map<String, Long> getAllVisits() {
        Map<String, Long> result = new HashMap<>();
        for(String url : allUrls.keySet()) {
            result.put(url, allUrls.get(url).getVisits());
        }

        return result;
    }


}
