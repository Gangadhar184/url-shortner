package com.example.url_shortner.repository;

import com.example.url_shortner.model.UrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<UrlMapping, Long> {

    Optional<UrlMapping> findByShortKey(String shortKey);
    boolean existsByShortKey(String shortKey);
    //fetch only original url
    @Query("""
            SELECT u.originalUrl
            FROM UrlMapping u
            WHERE u.shorKey = :shortKey
""")
    Optional<String> findOriginalUrlByShortKey(@Param("shortKey")String shortKey);

    //expiration check useful for redirect flow
    @Query(
            """
            SELECT u.originalUrl
            FROM UrlMapping u
            WHERE u.shortKey = :shortKey
                AND (u.expiresAt IS NULL OR u.expiresAt > CURRENT_TIMESTAMP)
"""
    )
    Optional<String> findValidOriginalUrl(@Param("shortKey") String shortKey);

}
