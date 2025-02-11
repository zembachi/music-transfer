package com.music.transfer.external.manager.handler.impl;

import com.music.transfer.dto.ExternalServiceTokenDto;
import com.music.transfer.dto.ExternalServiceType;
import com.music.transfer.dto.ResponseGetAuthenticatedServiceInfoDto;
import com.music.transfer.dto.TransferPlaylistDto;
import com.music.transfer.external.manager.handler.ExternalService;
import com.music.transfer.external.manager.request.context.AppRequestContextHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExternalServiceManager {

    private final List<ExternalService> services;

    private final AppRequestContextHolder appRequestContextHolder;

    private Map<ExternalServiceType, ExternalService> serviceMap;

    @PostConstruct
    public void onPostConstruct() {
        this.serviceMap = services
                .stream()
                .collect(Collectors.toMap(
                        ExternalService::getType,
                        Function.identity()
                ));
    }

    public List<ResponseGetAuthenticatedServiceInfoDto> getAuthInfo() {
        return serviceMap.entrySet().stream()
                .map(entry -> {
                            final var service = entry.getValue();
                            return new ResponseGetAuthenticatedServiceInfoDto(entry.getKey(),
                                    service.isAuthorized(appRequestContextHolder.get().appUser().getId()),
                                    service.urlToRedirect());
                        }
                ).toList();
    }

    public void transferPlaylist(TransferPlaylistDto transferPlaylistDto) {
        ExternalServiceTokenDto fromToken = transferPlaylistDto.getFromToken();
        ExternalServiceTokenDto toToken = transferPlaylistDto.getToToken();
//        ExternalService fromService = serviceMap.get(fromToken.getType());
//        ExternalService toService = serviceMap.get(toToken.getType());
//        PlaylistDto fromPlaylist = fromService.getPlaylist(fromToken, transferPlaylistDto.getFromPlaylistName());
//        PlaylistDto toPlaylist = toService.getPlaylist(toToken, transferPlaylistDto.getToPlaylistName());
//        for (TrackDto song : fromPlaylist.getTracks()) {
//            //TODO: проверить есть ли уже песня в новом плейлисте
//            FoundTrackDto foundSong = toService.findSong(song);
//            toService.addSongToPlaylist(toToken, toPlaylist.getName(), foundSong);
//            //TODO: послать запрос на сохранение в истории
//        }
    }

}
