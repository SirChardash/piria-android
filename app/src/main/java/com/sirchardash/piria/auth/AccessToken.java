package com.sirchardash.piria.auth;

import com.google.gson.annotations.SerializedName;

public class AccessToken {

    @SerializedName("access_token")
    private final String accessToken;
    @SerializedName("refresh_token")
    private final String refreshToken;
    @SerializedName("expires_in")
    private final long expiresIn;
    @SerializedName("refresh_expires_in")
    private final long refreshExpiresIn;
    @SerializedName("token_type")
    private final String tokenType;
    @SerializedName("session_state")
    private final String sessionState;

    public AccessToken(String accessToken,
                       String refreshToken,
                       long expiresIn,
                       long refreshExpiresIn,
                       String tokenType,
                       String sessionState) {
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
