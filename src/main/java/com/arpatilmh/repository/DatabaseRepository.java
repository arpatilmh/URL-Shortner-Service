package com.arpatilmh.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Repository
@Profile("database")
public class DatabaseRepository implements UrlRepository {

    @Autowired
    private final JpaUrlRepository jpaUrlRepository;

    private static final Logger logger = LoggerFactory.getLogger(DatabaseRepository.class);

    public DatabaseRepository(JpaUrlRepository jpaUrlRepository) {
        this.jpaUrlRepository = jpaUrlRepository;
    }

    @Override
    public void save(UrlEntity urlEntity) {
        jpaUrlRepository.save(urlEntity);
    }

    @Override
    public UrlEntity findByShortUrl(String shortUrl) {
        return jpaUrlRepository.findByShortUrl(shortUrl);
    }

    @Override
    public boolean exists(String shortUrl) {
        UrlEntity urlEntity = jpaUrlRepository.findByShortUrl(shortUrl);
        return urlEntity != null;
    }

    @Override
    public UrlEntity findByLongUrl(String longUrl) {
        return jpaUrlRepository.findByLongUrl(longUrl);
    }

    @Override
    public void incrementVisits(String shortUrl) {
        UrlEntity urlEntity = findByShortUrl(shortUrl);
        int updatedRows = jpaUrlRepository.incrementVisitsById(urlEntity.getId());
        logger.info("Number of updated rows: {}", updatedRows);
    }

    @Override
    public Map<String, Long> getAllVisits() {
        List<UrlEntity> allEntities = jpaUrlRepository.findAll();
        Map<String, Long> visits = new HashMap<>();
        for(UrlEntity urlEntity : allEntities) {
            visits.put(urlEntity.getShortUrl(), urlEntity.getVisits());
        }

        return visits;
    }
}
