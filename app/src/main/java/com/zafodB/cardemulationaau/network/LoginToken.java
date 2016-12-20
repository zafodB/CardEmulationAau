package com.zafodB.cardemulationaau.network;

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

    public String getToken() {
        return token;
    }


    public String getUser_id() {
        return user_id;
    }

}
