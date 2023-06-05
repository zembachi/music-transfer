package com.music.transfer.external.manager.dto;

import lombok.Data;

import java.util.List;

@Data
public class ResponseSpotifyGetPlaylistsDto {

    private Integer total;

    private List<SpotifyGetSimplifiedPlaylistDto> items;

}
