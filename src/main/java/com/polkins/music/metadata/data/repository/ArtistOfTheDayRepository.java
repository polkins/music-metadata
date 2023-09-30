package com.polkins.music.metadata.data.repository;

import com.polkins.music.metadata.data.model.Artist;
import com.polkins.music.metadata.data.model.ArtistOfTheDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.util.Optional;

public interface ArtistOfTheDayRepository extends JpaRepository<ArtistOfTheDay, Long>, JpaSpecificationExecutor<Artist> {

    Optional<ArtistOfTheDay> findByDate(LocalDate date);
}
