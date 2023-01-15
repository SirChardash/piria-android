package com.sirchardash.piria.repository;

import com.sirchardash.piria.model.TourContentEntry;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface TourContentRepository {

    @GET("/content/{contentId}")
    Call<ResponseBody> getContent(@Path("contentId") int contentId, @Header("x-tour-ticket") String ticket);

    @GET("/tours/{tourId}/content")
    Call<List<TourContentEntry>> getContentList(@Path("tourId") int tourId, @Header("x-tour-ticket") String ticket);

}
