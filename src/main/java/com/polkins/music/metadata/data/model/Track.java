package com.polkins.music.metadata.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import java.time.LocalDateTime;

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
    @GenericGenerator(
            name = "ID_GENERATOR",
            parameters = {
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "sequence_name", value = "track_sequence")
            })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_GENERATOR")
    private Long id;

    private String link;

    private String name;

    private LocalDateTime created;

    private String genre;

    private Long length;

    private Long duration;

    private String contentType;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "artist_uuid", nullable = false)
    private Artist artist;
}
