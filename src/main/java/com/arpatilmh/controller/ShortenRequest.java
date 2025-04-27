package com.arpatilmh.controller;

import java.io.Serializable;

public class ShortenRequest implements Serializable {
    private String url;
    private String alias;
    private Long expiration;

    // Default constructor (required for JSON deserialization)
    public ShortenRequest() {
    }

    // Parameterized constructor (optional)
    public ShortenRequest(String url, String alias, Long expiration) {
        this.url = url;
        this.alias = alias;
        this.expiration = expiration;
    }

    // Getters and Setters
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Long getExpiration() {
        return expiration;
    }

    public void setExpiration(Long expiration) {
        this.expiration = expiration;
    }

    @Override
    public String toString() {
        return new StringBuilder("ShortenRequest{")
                .append("url='").append(url).append('\'')
                .append(", alias='").append(alias).append('\'')
                .append(", expiration=").append(expiration)
                .append('}')
                .toString();
    }
}
