package com.polkins;

import com.polkins.music.metadata.service.ArtistService;
import com.polkins.music.metadata.service.impl.ArtistOfTheDayService;
import io.minio.MinioClient;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;

@Profile("test")
@SpringBootConfiguration
@ComponentScan(value = {"com.polkins"})
public class TestConfiguration {

    @MockBean
    MinioClient minioClient;

    @SpyBean
    ArtistService artistService;

    @SpyBean
    ArtistOfTheDayService artistOfTheDayService;
}
