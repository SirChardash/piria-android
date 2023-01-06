package com.sirchardash.piria.auth;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

public class KeycloakAuthenticator implements Authenticator {

    private final UserService userService;

    public KeycloakAuthenticator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Request authenticate(Route route, @NonNull Response response) {
        if (responseCount(response) > 3) {
            return null;
        }

        if (response.request().header("Authorization") != null) {
            userService.refresh();
        }

        return response.request().newBuilder()
                .header("Authorization", userService.getAuthorizationHeader())
                .build();
    }

    private static int responseCount(Response response) {
        int result = 1;
        while ((response = response.priorResponse()) != null) {
            result++;
        }
        return result;
    }

}
