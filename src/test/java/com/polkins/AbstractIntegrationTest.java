package com.polkins;

import com.polkins.music.metadata.MusicMetadataApplication;
import com.polkins.music.metadata.data.repository.ArtistOfTheDayRepository;
import com.polkins.music.metadata.data.repository.ArtistRepository;
import com.polkins.music.metadata.data.repository.TrackRepository;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import okhttp3.Headers;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@SpringBootTest(
        classes = {TestConfiguration.class, MusicMetadataApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
public abstract class AbstractIntegrationTest extends TestPostgresContainer {
    @Autowired
    protected MockMvc mvc;

    @Autowired
    protected MinioClient minioClient;

    @Autowired
    protected ArtistRepository artistRepository;

    @Autowired
    protected TrackRepository trackRepository;

    @Autowired
    protected ArtistOfTheDayRepository artistOfTheDayRepository;

    @BeforeEach
    void setup() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        Mockito.reset(minioClient);
        when(minioClient.putObject(any())).thenReturn(new ObjectWriteResponse(new Headers.Builder().build(), "tracks", "region", "object", "", ""));
    }
}
