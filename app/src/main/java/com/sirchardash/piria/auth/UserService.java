package com.sirchardash.piria.auth;

import com.sirchardash.piria.LoginFragment;
import com.sirchardash.piria.MainActivity;
import com.sirchardash.piria.repository.SimpleCallback;

import java.io.IOException;
import java.util.function.Consumer;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;

@Singleton
public class UserService {

    private static final String REALM_NAME = "bravesmart";
    private static final String CLIENT_ID = "bravesmart-web";

    private final KeycloakRepository repository;
    private AccessToken accessToken;
    private Consumer<AccessToken> accessTokenConsumer;

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

    public void refresh() {
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
            System.out.println(accessToken);
            accessTokenConsumer.accept(accessToken);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Call<UserInfo> getUserInfo() {
        return repository.userInfo(getAuthorizationHeader());
    }

    public void popLoginScreenIfNeeded(MainActivity activity) {
        getUserInfo().enqueue(new SimpleCallback<>(response -> {
            if (!response.isSuccessful()) {
                activity.navigateTo(new LoginFragment(), true);
            }
        }, error -> {
            activity.navigateTo(new LoginFragment(), true);
        }
        ));
    }

    public void setAccessToken(AccessToken accessToken) {
        this.accessToken = accessToken;
    }

    public void onRefresh(Consumer<AccessToken> accessTokenConsumer) {
        this.accessTokenConsumer = accessTokenConsumer;
    }

    public String getAuthorizationHeader() {
        return accessToken != null
                ? String.format("Bearer %s", accessToken.getAccessToken())
                : "";
    }

}
