package com.example.urlshortner.service;


import com.example.urlshortner.entity.UrlShortner;
import com.example.urlshortner.repository.UrlRepository;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UrlShortenerService {

    private final UrlRepository urlRepository;
    private   final ShortCodeGenerator shortCodeGenerator;

    public UrlShortenerService(UrlRepository urlRepository, ShortCodeGenerator shortCodeGenerator) {
        this.urlRepository = urlRepository;
        this.shortCodeGenerator = shortCodeGenerator;
    }


    private void  validate( String originalUrl)   {
        if (originalUrl == null || originalUrl.isBlank()) {
            throw new IllegalArgumentException("URL cannot be empty");
        }
        try {
        URI uri = new URI(originalUrl);

        String schema = uri.getScheme();
        if(schema == null ||
            !(schema.equalsIgnoreCase("http") ||
                    schema.equalsIgnoreCase("https")))
            {
            throw new IllegalArgumentException(
                    "Only HTTP and HTTPS URLs are allowed");
        }
            if (uri.getHost() == null || uri.getHost().isBlank()) {
                throw new IllegalArgumentException("Invalid URL: Host is missing");
            }
    }
        catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid URL format", e);
        }
    }

    public String createShortLink(String originalUrl)   {
        validate(originalUrl);
        for(int i =0;i<5; i++) {
            String code = shortCodeGenerator.generate();
            UrlShortner n = new UrlShortner(originalUrl, code);
            try {
                urlRepository.saveAndFlush(n);
                return code;
            } catch (org.springframework.dao.DataIntegrityViolationException e) {
                if (!isDuplicateKey(e)) {
                    throw e;
                }
        }
    }

        throw new IllegalStateException("Could not generate a unique short code after 5 attempts");
    }

    private boolean isDuplicateKey(Throwable error) {
        while (error != null) {
            if (error instanceof org.springframework.dao.DuplicateKeyException
                    || error instanceof org.hibernate.exception.ConstraintViolationException) {
                return true;
            }
            error = error.getCause();
        }
        return false;
    }

    public String resolve(String shortCode) {
        return urlRepository.findByShortCode(shortCode)
                .map(UrlShortner::getOriginalUrl)
                .orElseThrow(() -> new NoSuchElementException(
                        "Short code not found: " + shortCode));
    }

}
