package com.polkins.music.metadata.data.repository;

import com.polkins.music.metadata.data.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface ArtistRepository extends JpaRepository<Artist, UUID>, JpaSpecificationExecutor<Artist> {
    Optional<Artist> findArtistByPseudonym(String pseudonym);

    Optional<Artist> findTopByIsDailyFalse();

    @Modifying
    @Query("update Artist a set a.isDaily = false")
    void updateAllDailyFalse();
}
