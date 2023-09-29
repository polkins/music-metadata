package com.polkins.music.metadata.controller;

import com.polkins.music.metadata.dto.TrackDTO;
import com.polkins.music.metadata.dto.UploadDTO;
import com.polkins.music.metadata.service.TrackService;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/track")
public class TrackController {
    private final TrackService trackService;

    /**
     * Upload the track by artist`s pseudonym with additional information.
     *
     * @param file track to upload
     * @param uploadDTO - additional information
     * @return track metadata
     */
    @ApiOperation(value = "Upload file")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public TrackDTO upload(
            @RequestPart @Valid @NotNull MultipartFile file,
            @RequestPart @Valid @NotNull UploadDTO uploadDTO) {
        return trackService.upload(file, uploadDTO);
    }

    /**
     * Actually this method is made in a simple manner, when we return whole file to the front end by one request.
     * But if we speak about streaming platforms the way of giving file should be changed to streaming where file`s parts download by group of requests .
     * @param id - id of the track
     * @param pseudonym - pseudonym of the artist
     * @return - track file
     */
    @ApiOperation(value = "Play file by id and artist pseudonym")
    @GetMapping(value = "/play/{pseudonym}/{id}", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Resource> play(@PathVariable @NotNull Long id, @PathVariable @NotNull String pseudonym) {
        var pair = trackService.play(pseudonym, id);
        var track = pair.getRight();

        return ResponseEntity.ok()
                .contentLength(track.getLength())
                .contentType(MediaType.valueOf(track.getContentType()))
                .header(CONTENT_DISPOSITION, "attachment; filename=" + track.getTitle())
                .body(pair.getLeft());
    }

    /**
     * Get metadata about the track which is stored in minIO.
     *
     * @param id - file id
     * @return file dto
     */
    @ApiOperation(value = "Get track information")
    @GetMapping(value = "/{id}")
    public TrackDTO get(@PathVariable @NotNull Long id) {
        return trackService.get(id);
    }
}
