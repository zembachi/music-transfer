package com.music.transfer.external.manager.dto;

import lombok.Data;

import java.util.List;

@Data
public class SpotifyGetSimplifiedPlaylistDto {

    private String description;

    private String id;

    private String name;

    private List<SpotifyGetSimplifiedTrackDto> tracks;

}
