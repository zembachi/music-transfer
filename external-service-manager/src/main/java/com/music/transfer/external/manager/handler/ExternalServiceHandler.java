package com.music.transfer.external.manager.handler;

import com.music.transfer.dto.ExternalServiceTokenDto;
import com.music.transfer.dto.ExternalServiceType;
import com.music.transfer.dto.FoundSongDto;
import com.music.transfer.dto.PlaylistDto;
import com.music.transfer.dto.SongDto;
import org.springframework.lang.NonNull;

public interface ExternalServiceHandler {

    ExternalServiceType getType();

    @NonNull
    ExternalServiceTokenDto loginToExternalService(@NonNull String username,
                                                   @NonNull String password,
                                                   @NonNull ExternalServiceType type);

    void createPlaylist(@NonNull ExternalServiceTokenDto token, @NonNull String playlistName);

    PlaylistDto getPlaylist(@NonNull ExternalServiceTokenDto token, @NonNull String playlistName);

    FoundSongDto findSong(SongDto songDto);

    void addSongToPlaylist(@NonNull ExternalServiceTokenDto token, @NonNull String playlistName, @NonNull SongDto song);

}
