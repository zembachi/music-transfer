package com.music.transfer.external.manager.handler;

import com.music.transfer.dto.ExternalServiceTokenDto;
import com.music.transfer.dto.ExternalServiceType;
import com.music.transfer.dto.FoundTrackDto;
import com.music.transfer.dto.PlaylistDto;
import com.music.transfer.dto.TrackDto;
import org.springframework.lang.NonNull;

public interface ExternalServiceHandler extends ExternalServiceAuthorizer {

    ExternalServiceType getType();

    void createPlaylist(@NonNull ExternalServiceTokenDto token, @NonNull String playlistName);

    PlaylistDto getPlaylist(@NonNull ExternalServiceTokenDto token, @NonNull String playlistName);

    FoundTrackDto findSong(TrackDto trackDto);

    void addSongToPlaylist(@NonNull ExternalServiceTokenDto token, @NonNull String playlistName, @NonNull TrackDto song);

}
