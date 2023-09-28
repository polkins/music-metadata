package com.polkins.music.metadata.utils.specification;

import com.polkins.music.metadata.data.model.Artist;
import com.polkins.music.metadata.data.model.Artist_;
import com.polkins.music.metadata.data.model.Track;
import com.polkins.music.metadata.data.model.Track_;
import jakarta.persistence.criteria.Join;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

@UtilityClass
public class TrackSpecification {
    public static Specification<Track> findTrackByArtistPseudonym(String pseudonym, Long id) {
        return (root, query, criteriaBuilder) -> {
            Join<Track, Artist> join = root.join(Track_.artist);
            query.distinct(true);

            return criteriaBuilder.and(
                            criteriaBuilder.equal(root.get(Track_.id), id),
                            criteriaBuilder.equal(join.get(Artist_.pseudonym), pseudonym));
        };
    }
}
