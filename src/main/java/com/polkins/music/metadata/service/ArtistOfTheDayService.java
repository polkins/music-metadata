package com.polkins.music.metadata.service;

import com.polkins.music.metadata.data.model.ArtistOfTheDay;
import com.polkins.music.metadata.data.repository.ArtistOfTheDayRepository;
import com.polkins.music.metadata.data.repository.ArtistRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class ArtistOfTheDayService {

    private final ArtistRepository artistRepository;
    private final ArtistOfTheDayRepository artistOfTheDayRepository;

    // for test
//    private long delta = 0;

    @Transactional
    public void markNewArtistOfTheDay() {
//        LocalDate now = LocalDate.now().plusDays(delta++);
        LocalDate now = LocalDate.now();
        findByDayOrMark(now.minusDays(1));
        findByDayOrMark(now);
        findByDayOrMark(now.plusDays(1));
    }

    private void findByDayOrMark(LocalDate date) {
        if (artistOfTheDayRepository.findByDate(date).isPresent()) {
            return;
        }
        var artistOpt = artistRepository.findTopByIsDailyFalse();
        if (artistOpt.isEmpty()) {
            artistRepository.updateAllDailyFalse();
            artistOpt = artistRepository.findTopByIsDailyFalse();
        }
        var artist = artistOpt.orElseThrow(() -> new RuntimeException("No artists for update to daily"));
        ArtistOfTheDay newDaily = new ArtistOfTheDay()
                .setArtist(artist)
                .setDate(date);
        artistOfTheDayRepository.save(newDaily);
        artist.setIsDaily(Boolean.TRUE);
    }
}
