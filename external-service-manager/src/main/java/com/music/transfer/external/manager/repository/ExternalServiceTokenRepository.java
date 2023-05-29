package com.music.transfer.external.manager.repository;

import com.music.transfer.external.manager.entity.ExternalServiceToken;
import org.springframework.data.repository.CrudRepository;

import javax.validation.constraints.NotNull;

public interface ExternalServiceTokenRepository extends CrudRepository<ExternalServiceToken, Long> {

    ExternalServiceToken findByState(@NotNull String state);

}
