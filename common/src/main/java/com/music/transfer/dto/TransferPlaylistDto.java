package com.music.transfer.dto;

import lombok.Data;

@Data
public class TransferPlaylistDto {

    private ExternalServiceTokenDto fromToken;

    private ExternalServiceTokenDto toToken;

    private String fromPlaylistName;

    private String toPlaylistName;

}
