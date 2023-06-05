package com.music.transfer.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class FoundTrackDto extends TrackDto {

    private String uid;

}
