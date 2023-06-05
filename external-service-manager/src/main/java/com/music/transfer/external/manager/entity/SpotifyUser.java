package com.music.transfer.external.manager.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "seq_spotify_user", sequenceName = "seq_spotify_user", allocationSize = 10)
public class SpotifyUser {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_spotify_user")
    private Long id;

    private String spotifyId;

    private String country;

    private String email;

    private String displayName;

}
