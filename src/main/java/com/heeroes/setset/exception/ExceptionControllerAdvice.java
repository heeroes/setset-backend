package com.heeroes.setset.exception;

import com.heeroes.setset.common.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> globalException(RuntimeException e){
        log.debug("오류발생 : ", e.getMessage());
        return ResponseEntity.status(500).body(Response.error(e.getMessage()));
    }
}
