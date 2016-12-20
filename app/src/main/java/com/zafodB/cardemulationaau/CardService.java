package com.zafodB.cardemulationaau;

import android.nfc.cardemulation.HostApduService;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class CardService extends HostApduService {

    @Override
    public byte[] processCommandApdu(byte[] commandApdu, Bundle extras) {
        Log.i(Constants.TAG, "Command processed.");

        ApplicationMain mApplication = (ApplicationMain) getApplication();
        String userID = mApplication.getId();

        if (userID == null){
            Toast.makeText(mApplication,mApplication.getResources().getString(R.string.no_id_tst),Toast.LENGTH_LONG).show();
            return new byte[0];
        }

        return userID.getBytes();
    }

    @Override
    public void onDeactivated(int reason) {  }
}
