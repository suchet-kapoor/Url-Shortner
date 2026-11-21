package com.example.urlshortner.service;


import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class ShortCodeGenerator {

    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 7;
    private final SecureRandom random = new SecureRandom();

    public String generate(){

        StringBuilder s = new StringBuilder(CODE_LENGTH);
        for(int i=0;i<CODE_LENGTH;i++){
            s.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
        }
        return s.toString();
    }
   
}
