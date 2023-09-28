package com.polkins.music.metadata.dto;

import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@Accessors(chain = true)
public class UploadDTO {
    @NotNull
    @ApiModelProperty("Artist`s pseudonym")
    private String pseudonym;

    @NotNull
    @ApiModelProperty("Genre of the track")
    private String genre;
}
