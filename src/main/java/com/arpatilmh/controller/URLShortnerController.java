package com.arpatilmh.controller;

import com.arpatilmh.service.UrlDto;
import com.arpatilmh.service.UrlService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.*;

@RestController
public class URLShortnerController {

    @Autowired
    private UrlService urlService;

    private String ip;

    @Value("${server.port}")
    private int serverPort;

    public URLShortnerController() {
        try {
            this.ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            this.ip = "127.0.0.1";
        }
    }

    @GetMapping("/")
    public String index() {
        return "Hello, I am URL shortner service!";
    }

    @PostMapping(value = "/shorten", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity shorten(@RequestBody ShortenRequest requestBody) {
        // Return 400 if URL is not provided
        if (requestBody.getUrl() == null || requestBody.getUrl().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Url cannot be null!");
        }


        if (requestBody.getAlias() != null && !requestBody.getAlias().isEmpty()) {
            UrlDto urlDto = urlService.findByShortUrl(requestBody.getAlias());
            if (urlDto != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Custom alias already taken!");
            }
        }

        Date expirationDate = requestBody.getExpiration() == null
                ? null
                : new Date(requestBody.getExpiration() * 1000L);
        UrlDto urlDto = urlService.createShortUrl(requestBody.getUrl(), requestBody.getAlias(), expirationDate);

        String finalUrl = "http://" + ip +
                ":" +
                serverPort +
                "/" +
                urlDto.getShortUrl();
        return ResponseEntity.status(HttpStatus.OK).body("Short url is : " + finalUrl);
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity redirectToURL(@PathVariable String shortUrl) {
        UrlDto urlDto = urlService.findByShortUrl(shortUrl);
        if (urlDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Url not found!");
        }


        if (urlDto.getExpiration() != null && urlDto.getExpiration().before(new Date())) {
            urlService.incrementVisits(urlDto.getShortUrl());
            return ResponseEntity.status(HttpStatus.GONE).body("Short url has expired!");
        }

        urlService.incrementVisits(urlDto.getShortUrl());
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(urlDto.getLongUrl()))
                .build();
    }

    @GetMapping("/admin/stats")
    public ResponseEntity visitStats() {
        Map<String, Long> visits = urlService.getAllUrlVisits();
        return ResponseEntity.status(HttpStatus.OK).body(visits);
    }
}