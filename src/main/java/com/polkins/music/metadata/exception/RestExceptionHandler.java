package com.polkins.music.metadata.exception;

import com.polkins.music.metadata.dto.ExceptionDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class RestExceptionHandler {

    @ExceptionHandler({
            TrackNotFoundException.class,
            ArtistNotFoundException.class
    })
    public ResponseEntity<ExceptionDTO> handleNotFoundException(RuntimeException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionDTO().setMessage(ex.getMessage()));
    }

    @ExceptionHandler({
            PlayTrackException.class,
            UploadTrackException.class
    })
    public ResponseEntity<ExceptionDTO> handleTrackException(RuntimeException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionDTO().setMessage(ex.getMessage()));
    }

    @ExceptionHandler({
            InternalMusicMetadataException.class
    })
    public ResponseEntity<ExceptionDTO> handleInternalException(RuntimeException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionDTO().setMessage(ex.getMessage()));
    }
}
