package com.music.transfer.external.manager.handler.impl;

import com.music.transfer.dto.ExternalServiceTokenDto;
import com.music.transfer.dto.ExternalServiceType;
import com.music.transfer.dto.FoundSongDto;
import com.music.transfer.dto.PlaylistDto;
import com.music.transfer.dto.SongDto;
import com.music.transfer.external.manager.handler.ExternalServiceAuthorizer;
import com.music.transfer.external.manager.handler.ExternalServiceHandler;
import org.passay.PasswordGenerator;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class SpotifyServiceHandlerImpl implements ExternalServiceHandler, ExternalServiceAuthorizer {

    private RestTemplate restTemplate;

    @Override
    public ExternalServiceType getType() {
        return ExternalServiceType.SPOTIFY;
    }

    @Override
    public ExternalServiceTokenDto loginToExternalService(String username, String password, ExternalServiceType type) {
        return null;
    }

    @Override
    public void createPlaylist(ExternalServiceTokenDto token, String playlistName) {

    }

    @Override
    public PlaylistDto getPlaylist(ExternalServiceTokenDto token, String playlistName) {
        return null;
    }

    @Override
    public FoundSongDto findSong(SongDto songDto) {
        return null;
    }

    @Override
    public void addSongToPlaylist(ExternalServiceTokenDto token, String playlistName, SongDto song) {

    }

    @Override
    public void prepare() {

    }

    @Override
    public void confirm() {

    }

}
