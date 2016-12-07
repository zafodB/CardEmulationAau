package com.example.filip.cardemulationaau.network;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by filip on 23/11/2016.
 */

public interface MyRetrofitAPI {

    @Headers("Content-Type:application/json")
    @POST("/users")
    Call<LoginToken> createUser(@Body User user);

    @Headers("Content-Type:application/json")
    @POST("/cards/verify/{inst}")
    Call<CardForUser> createCard(@Header("Authorization") String token, @Path("inst") String inst, @Body
            CardForServer cardForServer);
}
