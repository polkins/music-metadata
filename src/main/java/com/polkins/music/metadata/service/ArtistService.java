package com.polkins.music.metadata.service;

import com.polkins.music.metadata.dto.ArtistDTO;
import com.polkins.music.metadata.dto.TrackDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.ZonedDateTime;

public interface ArtistService {
    Page<TrackDTO> findTracks(String pseudonym, Pageable pageable);

     ArtistDTO findArtistByPseudonym(String pseudonym);

    void update(ArtistDTO artistDTO);

    ArtistDTO create(ArtistDTO artistDTO);

    ArtistDTO findDaily(ZonedDateTime zonedDateTime);
}
