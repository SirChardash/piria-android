package com.sirchardash.piria.auth;

import com.sirchardash.piria.repository.SimpleCallback;

import java.io.IOException;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UserService {

    private static final String REALM_NAME = "bravesmart";
    private static final String CLIENT_ID = "bravesmart-web";

    private final KeycloakRepository repository;
    private AccessToken accessToken;
    private final SimpleCallback<AccessToken> setToken = new SimpleCallback<>(
            x -> {
                accessToken = x.body();
                System.out.printf(Locale.ENGLISH, "token request code: %d, set token: %b%n", x.code(), accessToken != null);
            },
            x -> System.out.println(x.getMessage())
    );

    @Inject
    public UserService(KeycloakRepository repository) {
        this.repository = repository;
    }

    public void login(String username, String password) {
        repository.login(
                REALM_NAME,
                "password",
                CLIENT_ID,
                username,
                password
        ).enqueue(setToken);
    }

    public void logout() {
        accessToken = null;
    }

    void refresh() {
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

    public String getAuthorizationHeader() {
        return accessToken != null
                ? String.format("Bearer %s", accessToken.getAccessToken())
                : null;
    }

}
