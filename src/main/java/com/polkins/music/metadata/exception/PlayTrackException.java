package com.polkins.music.metadata.exception;

import static com.polkins.music.metadata.exception.ErrorType.PLAY_TRACK_ERROR;

public class PlayTrackException extends RuntimeException {
    public PlayTrackException(String message) {
        super(PLAY_TRACK_ERROR.getCode() + " : " + message);
    }
}
