package com.polkins.music.metadata.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorType {
    INTERNAL_ERROR("internal.error"),
    ARTIST_NOT_FOUND("artist.not.found.error"),
    TRACK_NOT_FOUND("track.not.found"),
    PLAY_TRACK_ERROR("track.play.error"),
    UPLOAD_TRACK_ERROR("track.upload.error");

    private final String errorName;

    public String getCode() {
        return "music.metadata." + errorName;
    }
}