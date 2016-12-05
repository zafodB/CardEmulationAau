package com.example.filip.cardemulationaau.network;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.filip.cardemulationaau.ApplicationMain;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by filip on 01/12/2016.
 */

public class CardForUser {

    @SerializedName("card_id")String card_id;

    public String getCard_id() {
        return card_id;
    }

    public void setCard_id(String card_id) {
        this.card_id = card_id;
    }
}
