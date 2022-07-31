package com.sirchardash.piria.auth;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface KeycloakRepository {

    @FormUrlEncoded
    @POST("/auth/realms/bravesmart/protocol/openid-connect/token")
    Call<AccessToken> login(@Query("realmName") String realmName,
                            @Field("grant_type") String grantType,
                            @Field("client_id") String clientId,
                            @Field("username") String username,
                            @Field("password") String password);

    @FormUrlEncoded
    @POST("/auth/realms/bravesmart/protocol/openid-connect/token")
    Call<AccessToken> refresh(@Query("realmName") String realmName,
                              @Field("grant_type") String grantType,
                              @Field("client_id") String clientId,
                              @Field("refresh_token") String refreshToken);

}
