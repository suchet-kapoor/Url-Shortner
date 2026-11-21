package com.example.urlshortner.dto;

public class LinkResponse {
    private String shortUrl;

    public LinkResponse(String shortUrl) {
        this.shortUrl = shortUrl;
    }
    public String getShortUrl() {
        return shortUrl;
    }
    public void setShortUrl(String shortUrl){
        this.shortUrl = shortUrl;
    }
}
