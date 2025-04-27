package com.arpatilmh.service;

import java.util.Date;

public class UrlDto {
    private String shortUrl;
    private String longUrl;
    private Date expiration;
    private Long visits;

    public  UrlDto() {
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public Long getVisits() {
        return visits;
    }

    public void setVisits(Long visits) {
        this.visits = visits;
    }

    @Override
    public String toString() {
        return "UrlDto{" +
                "shortUrl='" + shortUrl + '\'' +
                ", longUrl='" + longUrl + '\'' +
                ", expiration=" + expiration +
                ", visits=" + visits +
                '}';
    }
}
