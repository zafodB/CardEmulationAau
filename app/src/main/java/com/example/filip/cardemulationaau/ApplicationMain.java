package com.example.filip.cardemulationaau;

import android.app.Application;
import android.content.SharedPreferences;

import java.util.UUID;

/**
 * Created by filip on 23/11/2016.
 */

public class ApplicationMain extends Application {

    SharedPreferences mySharedPref;

    @Override
    public void onCreate() {
        super.onCreate();

        mySharedPref = this.getSharedPreferences(Constants.MEMORY_LOGIN_REF, 0);

        /* Following if conditions are for testing purposes only */
        if (Constants.WIPE_ID) {
            SharedPreferences.Editor edit = mySharedPref.edit();
            edit.clear().commit();
        }
        if (Constants.CREATE_ID) {
            SharedPreferences.Editor edit = mySharedPref.edit();
            UUID id = UUID.randomUUID();
            edit.putString("id", id.toString()).commit();
        }
        if (Constants.WIPE_MEMORY) {
            mySharedPref = this.getSharedPreferences(Constants.MEMORY_CARDS_REF, 0);
            SharedPreferences.Editor myEditor = mySharedPref.edit();
            myEditor.clear().commit();
        }
    }

    /**
     *  Reads ID from SharedPreferences memory.
     * @return ID returned as String. If no ID is found, returns null.
     */
    public String getId() {
        mySharedPref = this.getSharedPreferences(Constants.MEMORY_LOGIN_REF, 0);

        return mySharedPref.getString(Constants.MEMORY_USER_ID_REF, null);
    }

    /**
     *  Reads Token from SharedPreferences memory.
     * @return Token returned as String. If no Token is found, returns null.
     */
    public String getToken() {
        mySharedPref = this.getSharedPreferences(Constants.MEMORY_LOGIN_REF, 0);

        return mySharedPref.getString(Constants.MEMORY_TOKEN_REF, null);
    }

}
