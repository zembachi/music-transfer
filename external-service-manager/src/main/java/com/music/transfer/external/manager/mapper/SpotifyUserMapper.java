package com.music.transfer.external.manager.mapper;

import com.music.transfer.external.manager.dto.ResponseSpotifyGetProfileDto;
import com.music.transfer.external.manager.entity.SpotifyUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface SpotifyUserMapper {

    @Mapping(source = "id", target = "spotifyId")
    SpotifyUser dtoToEntity(ResponseSpotifyGetProfileDto dto);

}
