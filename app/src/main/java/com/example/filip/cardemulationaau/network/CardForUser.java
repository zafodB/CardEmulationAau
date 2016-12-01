package com.example.filip.cardemulationaau.network;

import com.google.gson.annotations.SerializedName;

/**
 * Created by filip on 01/12/2016.
 */

public class CardForUser {
    @SerializedName("cardNumber")String cardNumber;

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
}
