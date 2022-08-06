package com.sirchardash.piria.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccessToken {

    private final String accessToken;
    private final String refreshToken;
    private final long expiresIn;
    private final long refreshExpiresIn;
    private final String tokenType;
    private final String sessionState;

    public AccessToken(@JsonProperty("access_token") String accessToken,
                       @JsonProperty("refresh_token") String refreshToken,
                       @JsonProperty("expires_in") long expiresIn,
                       @JsonProperty("refresh_expires_in") long refreshExpiresIn,
                       @JsonProperty("token_type") String tokenType,
                       @JsonProperty("session_state") String sessionState) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
        this.refreshExpiresIn = refreshExpiresIn;
        this.tokenType = tokenType;
        this.sessionState = sessionState;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public long getRefreshExpiresIn() {
        return refreshExpiresIn;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getSessionState() {
        return sessionState;
    }

    @Override
    public String toString() {
        return "AccessToken{" +
                "accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", expiresIn=" + expiresIn +
                ", refreshExpiresIn=" + refreshExpiresIn +
                ", tokenType='" + tokenType + '\'' +
                ", sessionState='" + sessionState + '\'' +
                '}';
    }
}
