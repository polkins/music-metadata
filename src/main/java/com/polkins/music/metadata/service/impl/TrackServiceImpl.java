package com.polkins.music.metadata.service.impl;

import com.polkins.music.metadata.configuration.MinioConfiguration;
import com.polkins.music.metadata.data.model.Artist;
import com.polkins.music.metadata.data.model.Track;
import com.polkins.music.metadata.data.repository.ArtistRepository;
import com.polkins.music.metadata.data.repository.TrackRepository;
import com.polkins.music.metadata.dto.TrackDTO;
import com.polkins.music.metadata.dto.UploadDTO;
import com.polkins.music.metadata.exception.ArtistNotFoundException;
import com.polkins.music.metadata.exception.InternalMusicMetadataException;
import com.polkins.music.metadata.exception.PlayTrackException;
import com.polkins.music.metadata.exception.TrackNotFoundException;
import com.polkins.music.metadata.exception.UploadTrackException;
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
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
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
        if (!AUDIO_MIME_TYPES.contains(file.getContentType()))
            throw new InternalMusicMetadataException("Incorrect type of file!");

        try {
            Artist artist = artistRepository.findArtistByPseudonym(upload.getPseudonym())
                    .orElseThrow(() -> new ArtistNotFoundException("No artist with a such pseudonym " + upload.getPseudonym()));

            Track track = new Track()
                    .setCreated(ZonedDateTime.now().withZoneSameInstant(ZoneOffset.UTC))
                    .setLength(file.getSize())
                    .setContentType(file.getContentType())
                    .setArtist(artist)
                    .setArtist(artist)
                    .setTitle(file.getOriginalFilename())
                    .setGenre(upload.getGenre());

            track = trackRepository.save(track);

            String link = artist.getUuid() + "/" + track.getId() + "/" + file.getOriginalFilename();
            saveS3(file, link);
            log.debug("Track is successfully saved to s3" + link);

            track.setLink(link);

            return trackMapper.toDto(track);
        } catch (Exception e) {
            throw new UploadTrackException("During uploading file occurred error: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public Pair<Resource, Track> play(String pseudonym, Long trackId) {
        Objects.requireNonNull(trackId);
        Objects.requireNonNull(pseudonym);

        var track = trackRepository.findOne(findTrackByArtistPseudonym(pseudonym, trackId))
                .orElseThrow(() -> new TrackNotFoundException("No track found!"));

        try (InputStream inputStream = minioClient.getObject(GetObjectArgs.builder()
                .bucket(configuration.getBucket())
                .object(track.getLink())
                .build());

             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()
        ) {
            IOUtils.copy(inputStream, outputStream);
            return Pair.of(new ByteArrayResource(outputStream.toByteArray()), track);
        } catch (Exception ex) {
            throw new PlayTrackException(String.format("Failed playing track <%s>", track.getTitle()));
        }
    }

    @Transactional(readOnly = true)
    public TrackDTO get(Long id) {
        return trackRepository.findById(id).map(trackMapper::toDto).orElseThrow(() -> new TrackNotFoundException("No track found with such id"));
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
