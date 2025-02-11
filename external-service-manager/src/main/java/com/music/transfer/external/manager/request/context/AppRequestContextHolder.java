package com.music.transfer.external.manager.request.context;

import lombok.RequiredArgsConstructor;
import org.springframework.core.NamedThreadLocal;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;

@Component
@RequiredArgsConstructor
public class AppRequestContextHolder {

    private static final String HOLDER_NAME = "requestContextHolder";

    private final ThreadLocal<RequestContext> holder = new NamedThreadLocal<>(HOLDER_NAME);

    public void set(@NonNull RequestContext requestContext) {
        holder.set(requestContext);
    }
                                                              
    @NonNull
    public RequestContext get() {
        return holder.get();
    }

    public void remove() {
        holder.remove();
    }

}
