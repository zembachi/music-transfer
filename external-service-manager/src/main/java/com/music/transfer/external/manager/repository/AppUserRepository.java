package com.music.transfer.external.manager.repository;

import com.music.transfer.external.manager.entity.AppUser;
import org.springframework.data.repository.CrudRepository;

public interface AppUserRepository extends CrudRepository<AppUser, String> {
}
