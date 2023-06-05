package com.music.transfer.external.manager.repository;

import com.music.transfer.external.manager.entity.SpotifyUser;
import org.springframework.data.repository.CrudRepository;

public interface SpotifyUserRepository extends CrudRepository<SpotifyUser, Long> {
}
