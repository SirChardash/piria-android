package com.sirchardash.piria.repository;

import com.sirchardash.piria.model.TourContentEntry;
import com.sirchardash.piria.model.Tours;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface TourRepository {

    @GET("/tours/{museumId}")
    Call<Tours> findByMuseumId(@Path("museumId") long museumId);

    @GET("/tours/upcoming/{museumId}")
    Call<Tours> findUpcomingByMuseumId(@Path("museumId") long museumId);

    @GET("/tours/previous")
    Call<Tours> listRecent();

    @GET("/tours/upcoming")
    Call<Tours> listUpcoming();

    @GET("/tours/booked")
    Call<Tours> listBooked();

}
