package com.polkins.music.metadata.scheduler;

import com.polkins.music.metadata.service.ArtistOfTheDayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class ArtistOfTheDayScheduler {

    private final ArtistOfTheDayService artistOfTheDayService;

    // for testing
//    @Scheduled(fixedRate = 5, timeUnit = TimeUnit.SECONDS)
    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.DAYS)
    public void schedule() {
        artistOfTheDayService.markNewArtistOfTheDay();
        log.debug("New artist of the day was marked!");
    }
}
