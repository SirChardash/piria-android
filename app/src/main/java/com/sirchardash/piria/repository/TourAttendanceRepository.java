package com.sirchardash.piria.repository;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TourAttendanceRepository {

    @POST("/attendance/{tourId}/{paymentId}")
    Call<Void> logAttendance(@Path("tourId") int tourId,
                             @Path("paymentId") String paymentId,
                             @Query("locale") String locale);

}
