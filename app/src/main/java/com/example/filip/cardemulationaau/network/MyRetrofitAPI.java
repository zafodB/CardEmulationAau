package com.example.filip.cardemulationaau.network;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by filip on 23/11/2016.
 */

public interface MyRetrofitAPI {

    @Headers("Content-Type:application/json")
    @POST("/users")
    Call<LoginToken> createUser(@Body User user);

    @Headers("Content-Type:application/json")
    @POST("/cards/verify")
    Call<CardForUser> createCard(@Header("Authorization") String token, @Body CardForServer cardForServer);
}
