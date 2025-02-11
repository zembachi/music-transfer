package com.music.transfer.telegram.oidc.repository;

import com.music.transfer.telegram.oidc.entity.TelegramUser;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TelegramRepository extends CrudRepository<TelegramUser, Long> {

    Optional<TelegramUser> findByState(@NotNull String state);

}
