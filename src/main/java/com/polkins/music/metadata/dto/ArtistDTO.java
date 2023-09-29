package com.polkins.music.metadata.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@Accessors(chain = true)
public class ArtistDTO {

    @ApiModelProperty("Artist`s uuid")
    private UUID uuid;

    @ApiModelProperty("Artist`s name")
    private String name;

    @ApiModelProperty("Artist`s surname")
    private String surname;

    @ApiModelProperty("Artist`s pseudonym or name of the band")
    private String pseudonym;

    @ApiModelProperty("Artist`s email")
    private String email;
}
