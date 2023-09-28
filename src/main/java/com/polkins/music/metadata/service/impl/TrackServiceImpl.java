package com.polkins.music.metadata.service.impl;

import com.polkins.music.metadata.configuration.MinioConfiguration;
import com.polkins.music.metadata.data.model.Artist;
import com.polkins.music.metadata.data.model.Track;
import com.polkins.music.metadata.data.repository.ArtistRepository;
import com.polkins.music.metadata.data.repository.TrackRepository;
import com.polkins.music.metadata.dto.TrackDTO;
import com.polkins.music.metadata.dto.UploadDTO;
import com.polkins.music.metadata.mapper.TrackMapper;
import com.polkins.music.metadata.service.TrackService;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

import static com.polkins.music.metadata.utils.specification.TrackSpecification.findTrackByArtistPseudonym;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrackServiceImpl implements TrackService {
    private final MinioClient minioClient;
    private final MinioConfiguration configuration;
    private final TrackRepository trackRepository;
    private final ArtistRepository artistRepository;
    private final TrackMapper trackMapper;
    private static final Long DEFAULT_PART_SIZE = 1024 * 1024 * 5L; // 5mb

    private static final Set<String> AUDIO_MIME_TYPES = Set.of("audio/mpeg", "audio/vorbis", "audio/x-flac");

    /**
     * @param file   - file mp3 or another format
     * @param upload - dto with metadata for file
     * @return trackDTO - metadata of the track
     */
    @Override
    @Transactional
    public TrackDTO upload(MultipartFile file, UploadDTO upload) {
        log.info("Start saving of the track" + file.getOriginalFilename());
        if (!AUDIO_MIME_TYPES.contains(file.getContentType())) throw new RuntimeException("Incorrect type of file!");

        try {
            Artist artist = artistRepository.findArtistByPseudonym(upload.getPseudonym())
                    .orElseThrow(() -> new RuntimeException("No artist with a such pseudonym " + upload.getPseudonym()));

            String link = artist.getUuid() + "/" + file.getOriginalFilename();
            saveS3(file, link);
            log.debug("Track is successfully saved to s3" + link);

            Track track = new Track()
                    .setCreated(LocalDateTime.now()) //TODO check UTC+0
                    .setLength(file.getSize())
                    .setContentType(file.getContentType())
                    .setArtist(artist)
                    .setLink(link)
                    .setGenre(upload.getGenre());

            return trackMapper.toDto(trackRepository.save(track));

        } catch (Exception e) {
            throw new RuntimeException("During uploading file occurred error: " + e.getMessage(), e);
        }
    }

    @Transactional(readOnly = true)
    public Pair<Resource, Track> download(String pseudonym, Long trackId) {
        Objects.requireNonNull(trackId);
        Objects.requireNonNull(pseudonym);

        var track = trackRepository.findOne(findTrackByArtistPseudonym(pseudonym, trackId))
                .orElseThrow(() -> new RuntimeException("No track found!"));

        try (InputStream inputStream = minioClient.getObject(GetObjectArgs.builder()
                .bucket(configuration.getBucket())
                .object(track.getLink())
                .build());

             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()
        ) {
            IOUtils.copy(inputStream, outputStream);
            return Pair.of(new ByteArrayResource(outputStream.toByteArray()), track);
        } catch (Exception ex) {
            throw new RuntimeException(String.format("Failed reading file with uuid <%s>", track.getLink()), ex);
        }
    }

    private void saveS3(MultipartFile file, String pathToFile) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        PutObjectArgs object = PutObjectArgs
                .builder()
                .bucket(configuration.getBucket())
                .object(pathToFile)
                .stream(file.getInputStream(), -1, DEFAULT_PART_SIZE)
                .contentType(file.getContentType())
                .build();
        minioClient.putObject(object);

        log.info("File {} is saved {}.", pathToFile, configuration.getBucket());
    }
}
