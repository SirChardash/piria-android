package com.sirchardash.piria.auth;

import com.sirchardash.piria.repository.MuseumRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthRepositoryModule {

    private final KeycloakRepository keycloakRepository;

    public AuthRepositoryModule() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8020/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        keycloakRepository = retrofit.create(KeycloakRepository.class);
    }

//    @Provides
//    @Singleton
    public UserService userService() {
        return new UserService(keycloakRepository);
    }

//    @Provides
//    @Singleton
    public KeycloakAuthenticator keycloakAuthenticator() {
        return new KeycloakAuthenticator(userService());
    }

}
