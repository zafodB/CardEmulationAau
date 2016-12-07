package com.example.filip.cardemulationaau.network;

import com.google.gson.annotations.SerializedName;

/**
 * Created by filip on 01/12/2016.
 */

public class User {

    /**
     * _id : {"$oid":"5835fb031d38c908a2f56fe9"}
     * email : test@rest.com
     * password : 1234
     * __v : 0
     */

    @SerializedName("email")private String email;
    @SerializedName("password")private String password;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}