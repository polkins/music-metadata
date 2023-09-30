package com.polkins;

import com.polkins.music.metadata.data.repository.ArtistOfTheDayRepository;
import com.polkins.music.metadata.data.repository.ArtistRepository;
import com.polkins.music.metadata.data.repository.TrackRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles("test")
@SpringBootTest(
        classes = {TestConfiguration.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
@EnableAutoConfiguration
public abstract class AbstractIntegrationTest extends TestPostgresContainer {
    @Autowired
    protected MockMvc mvc;

    @Autowired
    protected ArtistRepository artistRepository;

    @Autowired
    protected TrackRepository trackRepository;

    @Autowired
    protected ArtistOfTheDayRepository artistOfTheDayRepository;

    @BeforeEach
    void setup() {
        Mockito.reset();
    }

    @AfterEach
    public void clean() {
        artistRepository.deleteAll();
        trackRepository.deleteAll();
        artistOfTheDayRepository.deleteAll();
    }
}
