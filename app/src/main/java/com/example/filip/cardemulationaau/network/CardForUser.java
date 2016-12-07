package com.example.filip.cardemulationaau.network;

import com.google.gson.annotations.SerializedName;

/**
 * Created by filip on 01/12/2016.
 */

public class CardForUser {

    @SerializedName("card_id")String card_id;

    public String getCard_id() {
        return card_id;
    }

}
