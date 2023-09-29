package com.polkins.music.metadata.data.repository;

import com.polkins.music.metadata.data.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface ArtistRepository extends JpaRepository<Artist, UUID>, JpaSpecificationExecutor<Artist> {
    Optional<Artist> findArtistByPseudonym(String pseudonym);
}
