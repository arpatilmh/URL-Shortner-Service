package com.arpatilmh;

import jakarta.servlet.http.HttpServletResponse;
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
public class URLShortnerService {
    private static final String ALPHANUMERIC =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    private static final Random random = new Random();
    private Map<String, UrlMetadata> allUrls = new HashMap<>();
    private String ip;
    @Value("${server.port}")
    private int serverPort;

    public URLShortnerService() {
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

        String shortUrl;
        if (requestBody.getAlias() != null && !requestBody.getAlias().isEmpty()) {
            if (allUrls.containsKey(requestBody.getAlias())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Custom alias already taken!");
            } else {
                shortUrl = requestBody.getAlias();
            }
        } else {
            String sampleShortUrl = generateShortUrl();
            while(allUrls.containsKey(sampleShortUrl)) {
                sampleShortUrl = generateShortUrl();
            }

            shortUrl = sampleShortUrl;
        }

        Date date;
        if (requestBody.getExpiration() == null) {
            date = null;
        } else {
            date = new Date(requestBody.getExpiration() * 1000L);
        }

        allUrls.put(shortUrl, new UrlMetadata(requestBody.getUrl(), date));
        String finalUrl = "http://" + ip + ":" + String.valueOf(serverPort) + "/" + shortUrl;
        return ResponseEntity.status(HttpStatus.OK).body("Short url is : " + finalUrl);
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity redirectToURL(@PathVariable String shortUrl, HttpServletResponse response) {
        if (!allUrls.containsKey(shortUrl)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Url not found!");
        }

        UrlMetadata urlMetadata = allUrls.get(shortUrl);
        if (urlMetadata.getExpiration() != null && urlMetadata.getExpiration().before(new Date())) {
            urlMetadata.incrementVisit();
            return ResponseEntity.status(HttpStatus.GONE).body("Short url has expired!");
        }

        urlMetadata.incrementVisit();
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(urlMetadata.getLongUrl()))
                .build();
    }

    @GetMapping("/admin/stats")
    public ResponseEntity visitStats() {
        List<String> adminStats = allUrls.entrySet().stream()
                .map(p -> p.getKey() + " : " + p.getValue().getVisit())
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(adminStats);
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

}