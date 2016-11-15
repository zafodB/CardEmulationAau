package com.example.filip.cardemulationaau;

import android.app.Service;
import android.content.Intent;
import android.nfc.cardemulation.HostApduService;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class CardService extends HostApduService {

    public static final String TAG = "CardService2";

    @Override
    public byte[] processCommandApdu(byte[] commandApdu, Bundle extras) {
        Log.i(TAG, "Hura!");
        return new byte[] {1,2,3,4,5,6,7,8};
    }

    @Override
    public void onDeactivated(int reason) {

    }
}
