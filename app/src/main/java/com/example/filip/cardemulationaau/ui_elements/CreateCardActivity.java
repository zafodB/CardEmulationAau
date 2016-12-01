package com.example.filip.cardemulationaau.ui_elements;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.filip.cardemulationaau.R;
import com.example.filip.cardemulationaau.network.CardForServer;
import com.example.filip.cardemulationaau.network.CardForUser;
import com.example.filip.cardemulationaau.network.LoginToken;
import com.example.filip.cardemulationaau.network.MyRetrofitAPI;
import com.example.filip.cardemulationaau.network.RestService;

import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.filip.cardemulationaau.ApplicationMain.TAG;

public class CreateCardActivity extends AppCompatActivity {

    public static final String TAG = "myTag";

    EditText emailField;
    EditText pinField;
    Spinner institutionChooser;
    Button createCardButton;

    CreateCardActivity thisInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_card);

        emailField = (EditText) findViewById(R.id.enter_email_inst);
        pinField = (EditText) findViewById(R.id.enter_pin);
        institutionChooser = (Spinner) findViewById(R.id.institution_chooser);
        createCardButton = (Button) findViewById(R.id.commit_add);

        thisInstance = this;

        createCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean sucess = fetchInstitutionList();

                String emailInst = emailField.getText().toString();
//                Log.i(TAG, "Pin field text is: " + pinField.getText().toString());
                int pin = Integer.parseInt (pinField.getText().toString());




                int institutionNumber = institutionChooser.getSelectedItemPosition();

                //TODO implement data validation here


                authenticateWithServer(institutionNumber, emailInst, pin);

                SharedPreferences mySharedPref = thisInstance.getSharedPreferences("CardForServer Data", 0);
                SharedPreferences.Editor myEditor = mySharedPref.edit();

                UUID uuid = UUID.randomUUID();

                String keyName = "h0" + uuid.toString();
                String keyNumber = "h1" + uuid.toString();
                String keyInstitution = "h2" + uuid.toString();

//                myEditor.putString(keyName, emailInst);
//                Log.i(TAG, "Key (name): " + keyName + ", value: " + emailInst);
//
//                myEditor.putString(keyNumber, cardUID);
//                Log.i(TAG, "Key (#): " + keyNumber + ", value: " + cardUID);

                myEditor.putString(keyInstitution, String.valueOf(institutionNumber));
                Log.i(TAG, "Key(Institution): " + keyInstitution + ", value: " + String.valueOf(institutionNumber));

//                myEditor.commit();

                finish();
            }
        });
    }

    //TODO Get server connection to work.
    private void authenticateWithServer(int institution, String email, int pin) {
        MyRetrofitAPI service = RestService.getInstance();
        Call<String> call = service.createCard(wrapCardData(institution,email,pin));

        Log.i(TAG, "Enqueing call");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

//                int cardNumber = Integer.getInteger(response.body().getCardNumber());

                Log.i(TAG,"Card number is: " + response.body());

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.i(TAG, "Got negative response");
                Log.i(TAG, "The error is: " + t.getMessage());
            }
        });
    }


    CardForServer wrapCardData(int institution, String email, int pin) {

        CardForServer card = new CardForServer();
        card.setInstitution(institution);
        card.setEmail(email);
        card.setPin(pin);

        return card;
    }

    //TODO Load list of institution we support from the server
    private boolean fetchInstitutionList() {

        SharedPreferences mySharedPref = thisInstance.getSharedPreferences("Institutions", 0);
        SharedPreferences.Editor myEditor = mySharedPref.edit();

        myEditor.putInt("Aalborg University", 1);
        myEditor.putInt("DTU", 2);
        myEditor.putInt("Fitness World", 3);
        myEditor.commit();


        return false;
    }

}
