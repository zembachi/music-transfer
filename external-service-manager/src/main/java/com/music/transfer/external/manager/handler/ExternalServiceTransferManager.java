package com.music.transfer.external.manager.handler;

import com.music.transfer.dto.TransferPlaylistDto;
import jakarta.validation.constraints.NotNull;

public interface ExternalServiceTransferManager {

    void transferPlaylist(@NotNull TransferPlaylistDto transferPlaylistDto);

}
