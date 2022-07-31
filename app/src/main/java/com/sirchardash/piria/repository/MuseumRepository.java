package com.sirchardash.piria.repository;

import com.sirchardash.piria.model.Museum;
import com.sirchardash.piria.model.Museums;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MuseumRepository {

    @GET("/museums/{id}")
    Call<Museum> findById(@Path("id") long id, @Header("Authorization") String authorization);

    @GET("/museums")
    Call<Museums> find(@Query("language") String language, @Query("query") String query);

    @GET("/museums")
    Call<Museum> listAll(@Query("language") String language);

}
