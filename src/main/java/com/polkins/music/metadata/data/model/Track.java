package com.polkins.music.metadata.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Table(name = "track")
@EqualsAndHashCode(of = "id")
@ToString(exclude = "artist")
public class Track {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "track_generator")
    @SequenceGenerator(name = "track_generator", sequenceName = "track_sequence", allocationSize = 1)
    private Long id;

    private String link;

    private String title;

    private ZonedDateTime created;

    private String genre;

    private Long length;

    private Long duration;

    private String contentType;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "artist_uuid", nullable = false)
    private Artist artist;
}
