package com.polkins.music.metadata.scheduler;

import com.polkins.music.metadata.service.impl.ArtistOfTheDayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ArtistOfTheDayScheduler {

    private final ArtistOfTheDayService artistOfTheDayService;

    @Scheduled(fixedRateString = "${daily.artist.fixedRate}")
    public void schedule() {
        artistOfTheDayService.markNewArtistOfTheDay();
        log.debug("New artist of the day was marked!");
    }
}
