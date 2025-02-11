package com.music.transfer.thymeleaf.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "resource-server-feign", url = "${app.resource.server.url}/spotify")
public interface AppSpotifyFeignClient {

//
//    @GetMapping(value = "/prepare")
//    <Prepare> getAuthenticatedInfo();
}
