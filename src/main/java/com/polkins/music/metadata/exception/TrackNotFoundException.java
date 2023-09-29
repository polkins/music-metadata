package com.polkins.music.metadata.exception;

import static com.polkins.music.metadata.exception.ErrorType.TRACK_NOT_FOUND;

public class TrackNotFoundException extends RuntimeException {
    public TrackNotFoundException(String message) {
        super(TRACK_NOT_FOUND.getCode() + " : " + message);
    }
}
