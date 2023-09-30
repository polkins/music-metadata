package com.polkins.music.metadata.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Table(name = "artist_of_the_day")
@EqualsAndHashCode(of = "id")
public class ArtistOfTheDay {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "artist_of_the_day_generator")
    @SequenceGenerator(name = "artist_of_the_day_generator", sequenceName = "artist_of_the_day_sequence", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "artist_uuid", nullable = false)
    private Artist artist;

    private LocalDate date;
}
