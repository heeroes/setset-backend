package com.heeroes.setset.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Response<T> {

    private String message;
    private T result;

    public static <T> Response<T> success(T result){
        return new Response<>("success" ,result);
    }

    public static Response<String> error(String errorMessage){
        return new Response<>("error", errorMessage);
    }
}
