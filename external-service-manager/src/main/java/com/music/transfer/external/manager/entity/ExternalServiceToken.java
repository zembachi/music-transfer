package com.music.transfer.external.manager.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "seq_external_service_token", sequenceName = "seq_external_service_token", allocationSize = 10)
public class ExternalServiceToken {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_external_service_token")
    private Long id;

    private Long userId;

    private String accessToken;

    private String state;

    private String verifier;

    private String refreshToken;

    private Integer lifetime;

    private LocalDateTime lastUpdate;

}
