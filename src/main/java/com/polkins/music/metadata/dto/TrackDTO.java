package com.polkins.music.metadata.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@Accessors(chain = true)
public class TrackDTO {
    private Long id;

    private String title;

    private LocalDateTime created;

    private String genre;

    private Long length;

    private ArtistDTO artist;
}
