package com.example.filip.cardemulationaau;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.List;
import java.util.UUID;

/**
 * Created by filip on 23/11/2016.
 */

public class ApplicationMain extends Application {

    public static final String TAG = "myTag";

    public static final boolean WIPE_MEMORY = false;
    public static final boolean CREATE_ID = false;
    public static final boolean WIPE_ID = true;

    SharedPreferences mySharedPref;

    @Override
    public void onCreate() {
        super.onCreate();

        mySharedPref = this.getSharedPreferences("ID_storage",0);

        if (WIPE_ID){
            SharedPreferences.Editor edit = mySharedPref.edit();

            edit.clear().commit();
        }

        if (CREATE_ID){
            SharedPreferences.Editor edit = mySharedPref.edit();
            UUID id = UUID.randomUUID();

            edit.putString("id", id.toString()).commit();
        }

        if (WIPE_MEMORY) {
            mySharedPref = this.getSharedPreferences("Card Data", 0);
            SharedPreferences.Editor myEditor = mySharedPref.edit();
            myEditor.clear().commit();
        }
    }

    public String getId() {

        mySharedPref = this.getSharedPreferences("ID_storage",0);

        Log.i(TAG, "Sending ID");
        return mySharedPref.getString("id", null);
    }
}
