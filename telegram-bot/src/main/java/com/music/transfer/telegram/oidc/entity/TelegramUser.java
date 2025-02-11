package com.music.transfer.telegram.oidc.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TelegramUser {

    @Id
    private Long id;

    private String refreshToken;

    private String state;

}
