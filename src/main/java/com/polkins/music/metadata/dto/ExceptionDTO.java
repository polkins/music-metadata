package com.polkins.music.metadata.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ExceptionDTO {
    private String message;
}
