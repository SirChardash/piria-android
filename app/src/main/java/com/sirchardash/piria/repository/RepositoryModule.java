package com.sirchardash.piria.repository;

import com.sirchardash.piria.auth.KeycloakAuthenticator;
import com.sirchardash.piria.auth.KeycloakRepository;
import com.sirchardash.piria.auth.UserService;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class RepositoryModule {

    @Provides
    @Singleton
    @Named("authorizedRetrofit")
    public Retrofit authorizedRetrofit(KeycloakAuthenticator authenticator) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .authenticator(authenticator);
        return new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8081/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
    }

    @Provides
    @Singleton
    public MuseumRepository museumRepository(@Named("authorizedRetrofit") Retrofit retrofit) {
        return retrofit.create(MuseumRepository.class);
    }

    @Provides
    @Singleton
    public KeycloakRepository keycloakRepository() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://b5b7-185-125-122-29.eu.ngrok.io")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        return retrofit.create(KeycloakRepository.class);
    }

    @Provides
    @Singleton
    public UserService userService(KeycloakRepository keycloakRepository) {
        return new UserService(keycloakRepository);
    }

    @Provides
    @Singleton
    public KeycloakAuthenticator keycloakAuthenticator(UserService userService) {
        return new KeycloakAuthenticator(userService);
    }

}
