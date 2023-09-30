package com.polkins.music.metadata.controller;

import com.polkins.music.metadata.dto.ArtistDTO;
import com.polkins.music.metadata.dto.TrackDTO;
import com.polkins.music.metadata.service.ArtistService;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/artist")
public class ArtistController {

    private final ArtistService artistService;

    /**
     * Get artist`s tracks by pages.
     *
     * @param pseudonym artist`s pseudonym
     * @param page - page number
     * @param size - page size
     * @return page with tracks
     */
    @NotNull
    @ApiOperation(value = "Get all tracks of the artist by pseudonym")
    @GetMapping(value = "/{pseudonym}/tracks")
    public Page<TrackDTO> tracks(@PathVariable String pseudonym,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size) {
        return artistService.findTracks(pseudonym, PageRequest.of(page, size));
    }

    /**
     * Update artist.
     *
     * @param artistDTO - artist`s information
     */
    @NotNull
    @ApiOperation(value = "Update artist data")
    @PutMapping(value = "/update")
    public void update(@NotNull @RequestBody ArtistDTO artistDTO) {
        artistService.update(artistDTO);
    }

    /**
     * Get artist.
     *
     * @param pseudonym - artist`s pseydonym
     * @return artist`s information
     */
    @NotNull
    @ApiOperation(value = "Get artist data")
    @GetMapping(value = "/{pseudonym}")
    public ArtistDTO find(@PathVariable String pseudonym) {
        return artistService.findArtistByPseudonym(pseudonym);
    }

    /**
     * Get daily artist data
     *
     * @param zonedDateTime client`s date example 2007-12-03T10:15:30+01:00
     * @return artist dto
     */
    @NotNull
    @ApiOperation(value = "Get daily artist data")
    @GetMapping(value = "/daily")
    public ArtistDTO findDaily(@RequestParam ZonedDateTime zonedDateTime) {
        return artistService.findDaily(zonedDateTime);
    }

    /**
     * Create artist.
     *
     * @param artistDTO artist`s information
     * @return artist`s information with primary key from DB
     */
    @NotNull
    @ApiOperation(value = "Save artist data")
    @PostMapping
    public ArtistDTO create(@NotNull @RequestBody ArtistDTO artistDTO) {
        return artistService.create(artistDTO);
    }
}
