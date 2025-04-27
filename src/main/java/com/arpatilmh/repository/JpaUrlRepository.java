package com.arpatilmh.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface JpaUrlRepository extends JpaRepository<UrlEntity, Long> {
    UrlEntity findByShortUrl(String shortUrl);

    UrlEntity findByLongUrl(String longUrl);

    @Modifying
    @Transactional
    @Query("UPDATE UrlEntity u SET u.visits = u.visits + 1 WHERE u.id = :id")
    int incrementVisitsById(@Param("id") Long id);
}