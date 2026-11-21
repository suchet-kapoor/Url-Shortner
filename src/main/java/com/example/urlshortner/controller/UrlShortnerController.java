package com.example.urlshortner.controller;

import com.example.urlshortner.dto.LinkRequest;
import com.example.urlshortner.dto.LinkResponse;
import com.example.urlshortner.service.UrlShortenerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;


@RestController
@RequestMapping("/url")
public class UrlShortnerController {
    private final String shortUrlBase;

    private final  UrlShortenerService urlShortenerService;

    public UrlShortnerController(
            UrlShortenerService urlService,
            @Value("${app.short-url.base}") String shortUrlBase) {
        this.urlShortenerService = urlService;
        this.shortUrlBase = shortUrlBase;
    }

    @PostMapping("/code")
    public LinkResponse createShortUrl( @RequestBody LinkRequest request) {
        String originalUrl = request.getOriginalUrl();
        String shortCode = urlShortenerService.createShortLink(originalUrl);
        String shortUrl = shortUrlBase + "/url/" + shortCode;
        return new LinkResponse(shortUrl);
    }

    @GetMapping("/{code}")
    public ResponseEntity<Void> redirect(@PathVariable String code) {
        String originalUrl = urlShortenerService.resolve(code);
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create(originalUrl))
                .build();
    }
}
