package com.example.filip.cardemulationaau.network;

import com.google.gson.annotations.SerializedName;

/**
 * Created by filip on 01/12/2016.
 */

public class CardForServer {
    @SerializedName("institution") int institution;
    @SerializedName("email") String email;
    @SerializedName("pin") int pin;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public int getInstitution() {
        return institution;
    }

    public void setInstitution(int institution) {
        this.institution = institution;
    }
}
