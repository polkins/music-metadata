package com.polkins.music.metadata.service.impl;

import com.polkins.music.metadata.data.repository.ArtistRepository;
import com.polkins.music.metadata.data.repository.TrackRepository;
import com.polkins.music.metadata.dto.TrackDTO;
import com.polkins.music.metadata.mapper.TrackMapper;
import com.polkins.music.metadata.service.ArtistService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ArtistServiceImpl implements ArtistService {

    private final ArtistRepository artistRepository;

    private final TrackRepository trackRepository;

    private final TrackMapper trackMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<TrackDTO> findTracks(String pseudonym, Pageable pageable) {
        var tracks =  trackRepository.findTrackByArtistPseudonym(pseudonym , pageable)
                .stream().map(trackMapper::toDto)
                .collect(Collectors.toList());

        return new PageImpl<>(tracks, pageable, tracks.size());
    }
}
