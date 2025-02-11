package com.music.transfer.external.manager.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
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

    private String userId;

    private String accessToken;

    private String state;

    private String verifier;

    private String refreshToken;

    private Integer lifetime;

    private LocalDateTime lastUpdate;

}
