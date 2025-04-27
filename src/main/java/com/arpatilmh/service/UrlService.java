package com.arpatilmh.service;

import com.arpatilmh.repository.UrlEntity;
import com.arpatilmh.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.Random;


@Service
public class UrlService {

    private static final String ALPHANUMERIC =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final Random random = new Random();

    private final UrlRepository urlRepository;

    @Autowired
    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public UrlDto createShortUrl(String longUrl, String alias, Date expiration) {
        String newShortUrl = "";
        if (alias != null && !alias.isEmpty()) {
            newShortUrl = alias;
        } else {
            newShortUrl = generateShortUrl();
            if (urlRepository.exists(newShortUrl)) {
                newShortUrl = generateShortUrl();
            }
        }

        UrlEntity urlEntity = new UrlEntity(newShortUrl, longUrl, expiration);
        urlRepository.save(urlEntity);
        return convertToDTO(urlEntity);
    }

    public UrlDto findByLongUrl(String longUrl) {
        UrlEntity urlEntity = urlRepository.findByLongUrl(longUrl);
        if (urlEntity == null) {
            return null;
        }

        return convertToDTO(urlEntity);
    }

    /**
     * @param shortUrl short URL to be searched
     * @return UrlDto if found else null
     */
    public UrlDto findByShortUrl(String shortUrl) {
        UrlEntity urlEntity = urlRepository.findByShortUrl(shortUrl);
        if (urlEntity == null) {
            return null;
        }

        return convertToDTO(urlEntity);
    }

    public void incrementVisits(String shortUrl) {
        urlRepository.incrementVisits(shortUrl);
    }

    public Map<String, Long> getAllUrlVisits() {
        return urlRepository.getAllVisits();
    }



    private String generateShortUrl(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(ALPHANUMERIC.length());
            char randomChar = ALPHANUMERIC.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();
    }

    private String generateShortUrl() {
        return generateShortUrl(6);
    }


    private UrlDto convertToDTO(UrlEntity urlEntity) {
        UrlDto urlDto = new UrlDto();
        urlDto.setShortUrl(urlEntity.getShortUrl());
        urlDto.setLongUrl(urlEntity.getLongUrl());
        urlDto.setExpiration(urlEntity.getExpiration());
        urlDto.setVisits(urlEntity.getVisits());
        return urlDto;
    }

    private UrlEntity convertToEntity(UrlDto urlDto) {
        return new UrlEntity();
    }
}
