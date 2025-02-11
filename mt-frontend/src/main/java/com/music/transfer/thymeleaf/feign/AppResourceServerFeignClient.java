package com.music.transfer.thymeleaf.feign;

import com.music.transfer.dto.ResponseGetAuthenticatedServiceInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "resource-server", url = "${app.resource.server.url}")
public interface AppResourceServerFeignClient {

    @GetMapping(value = "/main/auth/info")
    List<ResponseGetAuthenticatedServiceInfoDto> getAuthenticatedInfo();

}
