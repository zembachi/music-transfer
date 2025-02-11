package com.music.transfer.telegram.mvc;

import com.music.transfer.telegram.config.properties.TelegramBotProperties;
import com.music.transfer.telegram.oidc.OidcService;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;

import java.net.URI;

@Controller
@Path("/auth")
@RequiredArgsConstructor
public class AuthEndpoint {

    private final OidcService oidcService;

    private final TelegramBotProperties telegramBotProperties;

    @GET
    @Produces("text/plain; charset=UTF-8")
    @SneakyThrows
    public Response auth(@QueryParam("state") String state,
                         @QueryParam("code") String code) {
        final var token = oidcService.finish(state, code);
        if (token != null) {
            final var botUri = new URI("tg://resolve?domain=" + telegramBotProperties.getName());
            return Response.temporaryRedirect(botUri).build();
        } else {
            return Response.serverError().entity("Cannot complete authentication").build();
        }
    }

}
