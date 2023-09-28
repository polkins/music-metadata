package com.polkins.music.metadata.mapper;

import com.polkins.music.metadata.data.model.Track;
import com.polkins.music.metadata.dto.TrackDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface TrackMapper extends AbstractMapper<Track, TrackDTO> {
}
