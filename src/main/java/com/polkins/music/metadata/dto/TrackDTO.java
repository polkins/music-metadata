package com.polkins.music.metadata.dto;

import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty("Track`s id")
    private Long id;

    @ApiModelProperty("Track`s title")
    private String title;

    @ApiModelProperty("Track`s duration")
    private LocalDateTime created;

    @ApiModelProperty("Track`s genere")
    private String genre;

    @ApiModelProperty("Track`s length")
    private Long length;

    @ApiModelProperty("Track`s artist")
    private ArtistDTO artist;
}
