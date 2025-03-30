package com.arpatilmh;

import java.util.Date;

class UrlMetadata {
    private String longUrl;
    private Date expiration;
    private int visit;

    public UrlMetadata(String longUrl, Date expiration) {
        this.longUrl = longUrl;
        this.expiration = expiration;
        this.visit = 0;
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

    public int getVisit() {
        return visit;
    }

    public void incrementVisit() {
        this.visit += 1;
    }
}
