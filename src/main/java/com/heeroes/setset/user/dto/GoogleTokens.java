package com.heeroes.setset.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GoogleTokens {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("refresh_token")
    private String refreshToken;
    @JsonProperty("token_type")
    private String tokenType;
    @JsonProperty("expires_in")
    private String expiresIn;
    @JsonProperty("scope")
    private String scope;
	@Override
	public String toString() {
		return "GoogleTokens [accessToken=" + accessToken + ", refreshToken=" + refreshToken + ", tokenType="
				+ tokenType + ", expiresIn=" + expiresIn + ", scope=" + scope + "]";
	}
    
    
}
