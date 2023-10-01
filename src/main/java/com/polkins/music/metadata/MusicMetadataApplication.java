package com.polkins.music.metadata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableJpaRepositories
@SpringBootApplication
public class MusicMetadataApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusicMetadataApplication.class, args);
    }

}
