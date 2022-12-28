package com.sirchardash.piria.auth;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Callback;

@Singleton
public class UserService {

    private static final String REALM_NAME = "bravesmart";
    private static final String CLIENT_ID = "bravesmart-web";

    private final KeycloakRepository repository;
    private AccessToken accessToken;

    @Inject
    public UserService(KeycloakRepository repository) {
        this.repository = repository;
    }

    public void login(String username, String password, Callback<AccessToken> callback) {
        repository.login(
                REALM_NAME,
                "password",
                CLIENT_ID,
                username,
                password
        ).enqueue(callback);
    }

    public void logout() {
        accessToken = null;
    }

    void refresh() {
        if (accessToken == null) {
            return;
        }
        try {
            accessToken = repository.refresh(
                    REALM_NAME,
                    "refresh_token",
                    CLIENT_ID,
                    accessToken.getRefreshToken()
            ).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setAccessToken(AccessToken accessToken) {
        this.accessToken = accessToken;
    }

    public String getAuthorizationHeader() {
        return accessToken != null
                ? String.format("Bearer %s", accessToken.getAccessToken())
                : "";
    }

}
