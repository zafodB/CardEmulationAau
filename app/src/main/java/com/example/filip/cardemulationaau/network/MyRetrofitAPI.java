package com.example.filip.cardemulationaau.network;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by filip on 23/11/2016.
 */

public interface MyRetrofitAPI {

    @Headers("Content-Type:application/json")
    @POST("/users")
    Call<LoginToken> createUser(@Body User user);
//    Call<ResponseBody> createUser(@Body User user);

}
