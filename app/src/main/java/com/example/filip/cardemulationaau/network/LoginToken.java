package com.example.filip.cardemulationaau.network;

import com.google.gson.annotations.SerializedName;

/**
 * Created by filip on 01/12/2016.
 */

public class LoginToken {

    /**
     * token : 123
     */

    @SerializedName("token") private String token;
    @SerializedName("user_id") private String user_id;
//    @SerializedName("_id") private String user_id;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
