package com.music.transfer.external.manager.repository;

import com.music.transfer.external.manager.entity.ExternalServiceToken;
import org.springframework.data.repository.CrudRepository;

import jakarta.validation.constraints.NotNull;

public interface ExternalServiceTokenRepository extends CrudRepository<ExternalServiceToken, Long> {

    ExternalServiceToken findByStateAndUserId(@NotNull String state, @NotNull String userId);

    ExternalServiceToken findByUserId(@NotNull String userId);

}
