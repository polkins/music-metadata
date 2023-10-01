package com.polkins.music.metadata.utils.specification;

import com.polkins.music.metadata.data.model.Artist;
import com.polkins.music.metadata.data.model.ArtistOfTheDay;
import com.polkins.music.metadata.data.model.ArtistOfTheDay_;
import com.polkins.music.metadata.data.model.Artist_;
import jakarta.persistence.criteria.ListJoin;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

@UtilityClass
public class ArtistSpecification {
    public static Specification<Artist> findDailyArtist(LocalDate date) {
        return (root, query, criteriaBuilder) -> {
            ListJoin<Artist, ArtistOfTheDay> join = root.joinList(Artist_.ARTIST_OF_THE_DAYS);
            query.distinct(true);

            return criteriaBuilder.equal(join.get(ArtistOfTheDay_.date), date);
        };
    }
}
