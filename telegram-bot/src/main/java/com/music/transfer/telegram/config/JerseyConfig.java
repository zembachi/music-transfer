package com.music.transfer.telegram.config;

import com.music.transfer.telegram.mvc.AuthEndpoint;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(AuthEndpoint.class);
    }

}
