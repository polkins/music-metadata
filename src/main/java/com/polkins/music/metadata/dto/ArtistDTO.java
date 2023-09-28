package com.polkins.music.metadata.dto;

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

    private UUID uuid;

    private String name;

    private String surname;

    private String pseudonym;

    private String email;
}
