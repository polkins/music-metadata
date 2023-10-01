package com.polkins;

import com.polkins.music.metadata.data.repository.ArtistRepository;
import com.polkins.music.metadata.dto.ArtistDTO;
import com.polkins.music.metadata.dto.UploadDTO;
import com.polkins.music.metadata.mapper.ArtistMapper;
import com.polkins.music.metadata.service.ArtistService;
import com.polkins.music.metadata.service.TrackService;
import com.polkins.music.metadata.service.impl.ArtistOfTheDayService;
import io.minio.MinioClient;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

/**
 * I know that all classes should be covered by tests but here I decided to test only logic from the task.
 * Other logic I tested by myself in insomnia.
 * Let`s decide that I have a hot sprint with fast increment for production where we should explore POC with usage of music service.
 */
class TaskIntegrationTests extends AbstractIntegrationTest {

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private TrackService trackService;

    @Autowired
    private ArtistService artistService;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private ArtistMapper artistMapper;

    @Autowired
    private ArtistOfTheDayService artistOfTheDayService;

    /**
     * Add a New Track: As a user, you should be able to add a new track to an artist's catalogue,
     * capturing attributes such as track title, genre, length, etc.
     */
    @Test
    void addNewTrack() {
        // given
        var artist = artistService.create(
                new ArtistDTO()
                        .setEmail("markinapln@gmail.com")
                        .setName("Polina")
                        .setSurname("Spiryaeva")
                        .setPseudonym("polkins")
        );
        var multipartFile = new MockMultipartFile("test", "test.mp3", "audio/mpeg", "test".getBytes());
        var uploadTestDto = new UploadDTO("polkins", "techno");

        // when
        var track = trackService.upload(multipartFile, uploadTestDto);
        var pageTracks = artistService.findTracks("polkins", Pageable.ofSize(1));

        // then
        assertThat(track).isNotNull();
        assertThat(track.getId()).isNotNull();
        assertThat(track.getGenre()).isEqualTo("techno");
        assertThat(track.getLength()).isEqualTo(multipartFile.getSize());
        assertThat(track.getCreated()).isNotNull();
        assertThat(track.getArtist()).isEqualTo(artist);
        assertThat(pageTracks.getContent()).hasSize(1);
        assertThat(pageTracks.getContent().get(0)).isEqualTo(track);
    }

    /**
     * As a user, you should be able to edit an artist's name to accommodate instances
     * where artists have multiple aliases.
     */
    @Test
    void editArtistName() {
        // given
        var artist = artistService.create(
                new ArtistDTO()
                        .setEmail("markinapln@gmail.com")
                        .setName("Polina")
                        .setSurname("Spiryaeva")
                        .setPseudonym("polkins2")
        );
        var multipartFile = new MockMultipartFile("test", "test.mp3", "audio/mpeg", "test".getBytes());
        var uploadTestDto = new UploadDTO("polkins2", "techno");

        // when
        trackService.upload(multipartFile, uploadTestDto);
        var pageTracks = artistService.findTracks("polkins2", Pageable.ofSize(1));
        var artistAfterUpdate = artistService.update(new ArtistDTO()
                .setUuid(artist.getUuid())
                .setEmail("markinapln@gmail.com")
                .setName("Arina")
                .setSurname("Spiryaeva")
                .setPseudonym("polkins2")
        );
        var pageTracksAfterUpdate = artistService.findTracks("polkins2", Pageable.ofSize(1));

        // then
        assertThat(pageTracks.getContent().get(0).getArtist().getSurname()).isEqualTo(artist.getSurname());
        assertThat(pageTracksAfterUpdate.getContent().get(0).getArtist().getSurname()).isEqualTo(artistAfterUpdate.getSurname());
    }


    /**
     * Fetch Artist Tracks: As a user, you should be able to fetch all tracks associated with a specific artist.
     */
    @Test
    void fetchAllTracks() {
        var artist = artistService.create(
                new ArtistDTO()
                        .setEmail("markinapln@gmail.com")
                        .setName("Polina")
                        .setSurname("Spiryaeva")
                        .setPseudonym("polkins3")
        );
        var multipartFile = new MockMultipartFile("test", "test.mp3", "audio/mpeg", "test".getBytes());
        var multipartFile2 = new MockMultipartFile("test2", "test2.mp3", "audio/mpeg", "test".getBytes());
        var uploadTestDto = new UploadDTO("polkins3", "techno");

        // when
        var track1 = trackService.upload(multipartFile, uploadTestDto);
        var track2 = trackService.upload(multipartFile2, uploadTestDto);
        var pageTracks = artistService.findTracks("polkins3", Pageable.ofSize(10));

        // then
        assertThat(pageTracks.getContent()).hasSize(2);
        assertThat(pageTracks.getContent()).containsExactlyInAnyOrderElementsOf(List.of(track1, track2));
    }

    /**
     * As a user, you should be able to see a different "Artist of the Day" in a cyclical
     * manner on the homepage each day, ensuring a fair rotation through the entire catalogue of artists.
     * This means if there are n artists, after n days, the cycle restarts with the first artist, ensuring an equal
     * chance for each artist to be the "Artist of the Day".
     */
    @Test
    @Ignore
    void artistOfTheDay() {
        artistService.create(
                new ArtistDTO()
                        .setEmail("markinapln@gmail.com")
                        .setName("Polina")
                        .setSurname("Spiryaeva")
                        .setPseudonym("polkins_coolest")
        );

        artistService.create(
                new ArtistDTO()
                        .setEmail("markinapln@gmail.com")
                        .setName("Polina")
                        .setSurname("Spiryaeva")
                        .setPseudonym("polkins_cooler")
        );

        await().atMost(Duration.ofSeconds(1))
                .untilAsserted(() -> verify(artistOfTheDayService).markNewArtistOfTheDay());

        var daily = artistService.findDaily(ZonedDateTime.now(ZoneId.of("Asia/Tbilisi")));

        assertThat(daily).isNotNull();
    }
}
