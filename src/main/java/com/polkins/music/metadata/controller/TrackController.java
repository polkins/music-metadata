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

    @ApiOperation(value = "Upload file")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public TrackDTO upload(
            @RequestPart @Valid @NotNull MultipartFile file,
            @RequestPart @Valid @NotNull UploadDTO uploadDTO) {
        return trackService.upload(file, uploadDTO);
    }

    @ApiOperation(value = "Play file by id and artist pseudonym")
    @GetMapping(value = "/play/{pseudonym}/{id}", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Resource> play(@PathVariable @NotNull Long id, @PathVariable @NotNull String pseudonym) {
        var pair = trackService.download(pseudonym, id);
        var track = pair.getRight();

        return ResponseEntity.ok()
                .contentLength(track.getLength())
                .contentType(MediaType.valueOf(track.getContentType()))
                .header(CONTENT_DISPOSITION, "attachment; filename=" + track.getTitle())
                .body(pair.getLeft());
    }

//    @ApiOperation(value = "Upload file")
//    @GetMapping(value = "/{id}")
//    public TrackDTO get(@PathVariable @NotNull Long id) {
//        return trackService.findTrack(id);
//    }
}
