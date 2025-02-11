package com.music.transfer.external.manager.request.filter;

import com.music.transfer.external.manager.entity.AppUser;
import com.music.transfer.external.manager.repository.AppUserRepository;
import com.music.transfer.external.manager.request.context.RequestContext;
import com.music.transfer.external.manager.request.context.AppRequestContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
@Component
public class AppRequestContextFilter extends OncePerRequestFilter {

    private final AppUserRepository appUserRepository;

    private final AppRequestContextHolder appRequestContextHolder;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        log.debug("adding user info to context");
        final var userUuid = SecurityContextHolder.getContext().getAuthentication().getName();
        final var userOptional = appUserRepository.findById(userUuid);
        AppUser user;
        if (userOptional.isEmpty()) {
            log.info("user not found in database, saving");
            user = AppUser.builder().id(userUuid).build();
            appUserRepository.save(user);
        } else {
            user = userOptional.get();
        }
        RequestContext context = new RequestContext(user);
        appRequestContextHolder.set(context);
        filterChain.doFilter(request, response);
    }

}
