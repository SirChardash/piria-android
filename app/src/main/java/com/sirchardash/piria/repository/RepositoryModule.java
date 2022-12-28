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
import retrofit2.converter.jackson.JacksonConverterFactory;

@Module
public class RepositoryModule {

    public static final String SERVER_URL = "http://34.118.42.41";
    public static final String KEYCLOAK_REGISTER_URL = SERVER_URL + ":8020/auth/realms/bravesmart/protocol/openid-connect/registrations?client_id=bravesmart-web&response_type=code";

    @Provides
    @Singleton
    @Named("authorizedRetrofit")
    public Retrofit authorizedRetrofit(KeycloakAuthenticator authenticator) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .authenticator(authenticator);
        return new Retrofit.Builder()
                .baseUrl(SERVER_URL + ":8080/")
                .addConverterFactory(JacksonConverterFactory.create())
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
    public TourRepository tourRepository(@Named("authorizedRetrofit") Retrofit retrofit) {
        return retrofit.create(TourRepository.class);
    }

    @Provides
    @Singleton
    public TrackingRepository trackingRepository(@Named("authorizedRetrofit") Retrofit retrofit) {
        return retrofit.create(TrackingRepository.class);
    }

    @Provides
    @Singleton
    public NewsRepository newsRepository(@Named("authorizedRetrofit") Retrofit retrofit) {
        return retrofit.create(NewsRepository.class);
    }

    @Provides
    @Singleton
    public KeycloakRepository keycloakRepository() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SERVER_URL + ":8020")
                .addConverterFactory(JacksonConverterFactory.create())
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
