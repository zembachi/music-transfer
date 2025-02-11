package com.music.transfer.telegram.feign;

import com.music.transfer.dto.ResponseGetAuthenticatedServiceInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "resource-server-user", url = "${app.resource.server.url}")
public interface UserStorageFeignClient {

    @GetMapping(value = "/main/auth/info")
    List<ResponseGetAuthenticatedServiceInfoDto> getAuthenticatedInfo();

}
