package com.polkins.music.metadata.data.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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

import java.util.List;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Table(name = "artist")
@EqualsAndHashCode(of = "uuid")
@ToString(exclude = "tracks")
public class Artist {

    @Id
    @GeneratedValue
    private UUID uuid;

    private String name;

    private String surname;

    @Column(unique = true)
    private String pseudonym;

    private String email;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "artist", fetch = FetchType.LAZY)
    private List<Track> tracks;
}
