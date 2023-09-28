package com.polkins.music.metadata.service;

import com.polkins.music.metadata.data.model.Track;
import com.polkins.music.metadata.dto.TrackDTO;
import com.polkins.music.metadata.dto.UploadDTO;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface TrackService {

    TrackDTO upload(MultipartFile file, UploadDTO uploadDTO);

    Pair<Resource, Track> download(String pseudonym, Long trackId);
}
