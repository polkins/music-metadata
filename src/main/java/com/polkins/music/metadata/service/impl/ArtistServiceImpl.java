package com.polkins.music.metadata.service.impl;

import com.polkins.music.metadata.data.repository.ArtistRepository;
import com.polkins.music.metadata.data.repository.TrackRepository;
import com.polkins.music.metadata.dto.ArtistDTO;
import com.polkins.music.metadata.dto.TrackDTO;
import com.polkins.music.metadata.exception.ArtistNotFoundException;
import com.polkins.music.metadata.mapper.ArtistMapper;
import com.polkins.music.metadata.mapper.TrackMapper;
import com.polkins.music.metadata.service.ArtistService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

import static com.polkins.music.metadata.utils.specification.ArtistSpecification.findDailyArtist;

@Service
@AllArgsConstructor
public class ArtistServiceImpl implements ArtistService {

    private final ArtistRepository artistRepository;

    private final TrackRepository trackRepository;

    private final TrackMapper trackMapper;

    private final ArtistMapper artistMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<TrackDTO> findTracks(String pseudonym, Pageable pageable) {
        var tracks = trackRepository.findTrackByArtistPseudonym(pseudonym, pageable)
                .stream().map(trackMapper::toDto)
                .toList();

        return new PageImpl<>(tracks, pageable, tracks.size());
    }

    @Override
    @Transactional(readOnly = true)
    public ArtistDTO findArtistByPseudonym(String pseudonym) {
        return artistRepository.findArtistByPseudonym(pseudonym)
                .map(artistMapper::toDto)
                .orElseThrow(() -> new ArtistNotFoundException("No artist found with pseudonym - " + pseudonym));
    }

    @Override
    @Transactional
    public void update(ArtistDTO artistDTO) {
        var artist = artistRepository.findById(artistDTO.getUuid());

        if (artist.isPresent()) {
            artist.get().setEmail(artistDTO.getEmail());
            artist.get().setName(artistDTO.getName());
            artist.get().setSurname(artistDTO.getSurname());
            artist.get().setPseudonym(artistDTO.getPseudonym());
        }
    }

    @Override
    @Transactional
    public ArtistDTO create(ArtistDTO artistDTO) {
        return artistMapper.toDto(artistRepository.save(artistMapper.toDomainModel(artistDTO)));
    }

    @Override
    public ArtistDTO findDaily(ZonedDateTime zonedDateTime) {
        var date = zonedDateTime.toLocalDate();
        var dailyArtist = artistRepository.findOne(findDailyArtist(date))
                .orElseThrow(() -> new ArtistNotFoundException("No daily artist found"));
        return artistMapper.toDto(dailyArtist);
    }
}
