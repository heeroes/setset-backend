package com.heeroes.setset.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.heeroes.setset.user.dto.NaverInfoResponse.Response;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleInfoResponse implements OAuthInfoResponse{
    @JsonProperty("email")
    private String email;
    @JsonProperty("name")
    private String name;


    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getNickName() {
        return name;
    }

    @Override
    public OAuthProvider getOAuthProvider() {
        return OAuthProvider.GOOGLE;
    }
}
