package com.zafodB.cardemulationaau.network;

import com.google.gson.annotations.SerializedName;

/**
 * Created by filip on 01/12/2016.
 */

public class CardForServer {
    @SerializedName("institution") int institution;
    @SerializedName("email") String email;
    @SerializedName("pin") int pin;


    public void setEmail(String email) {
        this.email = email;
    }


    public void setPin(int pin) {
        this.pin = pin;
    }

    public void setInstitution(int institution) {
        this.institution = institution;
    }
}
