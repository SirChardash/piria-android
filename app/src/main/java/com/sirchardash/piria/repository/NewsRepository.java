package com.sirchardash.piria.repository;

import com.sirchardash.piria.model.NewsEntry;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NewsRepository {

    @GET("/news")
    Call<List<NewsEntry>> list();

}
