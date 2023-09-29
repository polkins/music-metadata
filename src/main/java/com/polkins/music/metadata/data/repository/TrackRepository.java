package com.polkins.music.metadata.data.repository;

import com.polkins.music.metadata.data.model.Track;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface TrackRepository extends PagingAndSortingRepository<Track, Long>, JpaRepository<Track, Long>, JpaSpecificationExecutor<Track> {
    List<Track> findTrackByArtistPseudonym(String pseudonym, Pageable pageable);
}
