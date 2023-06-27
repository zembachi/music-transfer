package com.music.transfer.thymeleaf.model;

import com.music.transfer.dto.ExternalServiceType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExternalServiceInfo {

    private Long id;

    private String name;

    private ExternalServiceType type;

    private boolean status;

    private String href;

}
