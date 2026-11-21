package com.example.urlshortner.dto;

public class LinkRequest {
    private String originalUrl;


    public String getOriginalUrl(){
        return originalUrl;
    }
    public void setOriginalUrl(String originalUrl)
    {
        this.originalUrl = originalUrl;
    }
}
