package com.music.transfer.external.manager.handler;

import com.music.transfer.dto.ExternalServiceTokenDto;
import com.music.transfer.dto.FoundTrackDto;
import com.music.transfer.dto.PlaylistDto;
import com.music.transfer.dto.TrackDto;
import jakarta.validation.constraints.NotNull;

public interface ExternalServicePlaylistManager {

    void createPlaylist(@NotNull ExternalServiceTokenDto token, @NotNull String playlistName);

    PlaylistDto getPlaylist(@NotNull ExternalServiceTokenDto token, @NotNull String playlistName);

    FoundTrackDto findSong(TrackDto trackDto);

    void addSongToPlaylist(@NotNull ExternalServiceTokenDto token, @NotNull String playlistName, @NotNull TrackDto song);

}
