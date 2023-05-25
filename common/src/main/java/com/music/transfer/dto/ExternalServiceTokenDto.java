package com.music.transfer.dto;

import lombok.Data;

@Data
public class ExternalServiceTokenDto {

    private ExternalServiceType type;

    private String token;

}
