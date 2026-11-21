package com.example.urlshortner.repository;

import com.example.urlshortner.entity.UrlShortner;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UrlRepository extends JpaRepository<UrlShortner, Long> {
    Optional<UrlShortner> findByShortCode(String shortCode);
    boolean existsByShortCode(String shortCode);
}
