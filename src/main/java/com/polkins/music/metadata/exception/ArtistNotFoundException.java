package com.polkins.music.metadata.exception;

import static com.polkins.music.metadata.exception.ErrorType.ARTIST_NOT_FOUND;

public class ArtistNotFoundException extends RuntimeException {
    public ArtistNotFoundException(String message) {
        super(ARTIST_NOT_FOUND.getCode() + " : " + message);
    }
}
