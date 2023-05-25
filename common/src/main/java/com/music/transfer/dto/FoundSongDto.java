package com.music.transfer.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class FoundSongDto extends SongDto {

    private String uid;

}
