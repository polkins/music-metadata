package com.polkins.music.metadata.exception;

import static com.polkins.music.metadata.exception.ErrorType.INTERNAL_ERROR;

public class InternalMusicMetadataException extends RuntimeException {
    public InternalMusicMetadataException(String message) {
        super(INTERNAL_ERROR.getCode() + " : " + message);
    }
}
