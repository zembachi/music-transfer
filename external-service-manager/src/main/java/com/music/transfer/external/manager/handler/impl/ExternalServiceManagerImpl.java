package com.music.transfer.external.manager.handler.impl;

import com.music.transfer.dto.ExternalServiceTokenDto;
import com.music.transfer.dto.ExternalServiceType;
import com.music.transfer.dto.FoundTrackDto;
import com.music.transfer.dto.PlaylistDto;
import com.music.transfer.dto.TrackDto;
import com.music.transfer.dto.TransferPlaylistDto;
import com.music.transfer.external.manager.handler.ExternalServiceHandler;
import com.music.transfer.external.manager.handler.ExternalServiceManager;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;

@Service
public class ExternalServiceManagerImpl implements ExternalServiceManager {

    private Map<ExternalServiceType, ExternalServiceHandler> handlerMap;

    @PostConstruct
    public void onPostConstruct() {
        //TODO: собрать мапу
    }

    @Override
    public void transferPlaylist(TransferPlaylistDto transferPlaylistDto) {
        ExternalServiceTokenDto fromToken = transferPlaylistDto.getFromToken();
        ExternalServiceTokenDto toToken = transferPlaylistDto.getToToken();
        ExternalServiceHandler fromService = handlerMap.get(fromToken.getType());
        ExternalServiceHandler toService = handlerMap.get(toToken.getType());
        PlaylistDto fromPlaylist = fromService.getPlaylist(fromToken, transferPlaylistDto.getFromPlaylistName());
        PlaylistDto toPlaylist = toService.getPlaylist(toToken, transferPlaylistDto.getToPlaylistName());
        for (TrackDto song : fromPlaylist.getTracks()) {
            //TODO: проверить есть ли уже песня в новом плейлисте
            FoundTrackDto foundSong = toService.findSong(song);
            toService.addSongToPlaylist(toToken, toPlaylist.getName(), foundSong);
            //TODO: послать запрос на сохранение в истории
        }
    }

}
