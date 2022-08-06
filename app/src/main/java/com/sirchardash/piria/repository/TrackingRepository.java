package com.sirchardash.piria.repository;

import com.sirchardash.piria.model.TrackingLog;

import retrofit2.http.Body;
import retrofit2.http.POST;

public interface TrackingRepository {

    @POST
    void save(@Body TrackingLog trackingLog);

}
