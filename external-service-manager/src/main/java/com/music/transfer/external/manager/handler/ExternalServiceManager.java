package com.music.transfer.external.manager.handler;

import com.music.transfer.dto.TransferPlaylistDto;
import org.springframework.lang.NonNull;

public interface ExternalServiceManager {

    void transferPlaylist(@NonNull TransferPlaylistDto transferPlaylistDto);

}
