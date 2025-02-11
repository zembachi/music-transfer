package com.music.transfer.external.manager.controller;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

//    @Override
//    @NotNull
//    protected ResponseEntity<Object> handleExceptionInternal(@NotNull Exception ex,
//                                                             @Nullable Object body,
//                                                             @NotNull HttpHeaders headers,
//                                                             @NotNull HttpStatus status,
//                                                             @NotNull WebRequest request) {
//        log.error("Unhandled unexpected error", ex);
//        return ResponseEntity.ok(BaseResponseMapper.getErrorBaseResponseDto(ExpCode.UNEXPECTED_ERROR,
//                "Unhandled unexpected error",
//                requestContext.getRequestId()));
//    }

}
