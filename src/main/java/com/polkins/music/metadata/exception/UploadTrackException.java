package com.polkins.music.metadata.exception;

import static com.polkins.music.metadata.exception.ErrorType.UPLOAD_TRACK_ERROR;

public class UploadTrackException extends RuntimeException {
    public UploadTrackException(String message) {
        super(UPLOAD_TRACK_ERROR.getCode() + " : " + message);
    }
}
