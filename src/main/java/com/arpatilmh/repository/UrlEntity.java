package com.arpatilmh.repository;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "UrlEntity")
public class UrlEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String shortUrl;

    @Column(nullable = false)
    private String longUrl;

    @Column
    private Date expiration;

    @Column(nullable = false)
    private Long visits;

    public UrlEntity() {

    }

    public UrlEntity(String shortUrl, String longUrl, Date expiration) {
        this.id = null;
        this.shortUrl = shortUrl;
        this.longUrl = longUrl;
        this.expiration = expiration;
        this.visits = 0L;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        return new StringBuilder("UrlEntity{")
                .append("id=").append(id)
                .append(", shortUrl='").append(shortUrl).append('\'')
                .append(", longUrl='").append(longUrl).append('\'')
                .append(", expiration=").append(expiration)
                .append(", visits=").append(visits)
                .append('}')
                .toString();
    }
}