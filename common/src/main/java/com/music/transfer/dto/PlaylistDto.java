package com.music.transfer.dto;

import lombok.Data;

import java.util.List;

@Data
public class PlaylistDto {

    private String name;

    private List<SongDto> songs;
}
