package com.polkins.music.metadata.mapper;

import com.polkins.music.metadata.data.model.Artist;
import com.polkins.music.metadata.dto.ArtistDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ArtistMapper extends AbstractMapper<Artist, ArtistDTO> {
}
