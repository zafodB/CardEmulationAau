package com.example.filip.cardemulationaau.ui_elements;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.filip.cardemulationaau.R;

import java.util.UUID;

public class CreateCardActivity extends AppCompatActivity {

    public static final String TAG = "myTag";

    EditText cardNameField;
    Spinner institutionChooser;
    Button createCardButton;

    CreateCardActivity thisInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_card);

        cardNameField = (EditText) findViewById(R.id.enter_email_inst);
        institutionChooser = (Spinner) findViewById(R.id.institution_chooser);
        createCardButton = (Button) findViewById(R.id.commit_add);

        thisInstance = this;

        createCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isValid = true;

                boolean sucess = fetchInstitutionList();

                String cardName = cardNameField.getText().toString();
                int institutionNumber = institutionChooser.getSelectedItemPosition();

                if (cardName.isEmpty()){
                    isValid = false;
                    Toast.makeText(getApplicationContext(), getString(R.string.no_card_name_tst),Toast.LENGTH_LONG).show();
                } else if (cardName.length()>30){
                    isValid = false;
                    Toast.makeText(getApplicationContext(), getString(R.string.name_too_long_tst), Toast.LENGTH_LONG).show();
                }

                if (isValid){

                    String cardUID = authenticateWithServer(institutionNumber);

                    cardUID = UUID.randomUUID().toString();

                    SharedPreferences mySharedPref = thisInstance.getSharedPreferences("Card Data",0);
                    SharedPreferences.Editor myEditor = mySharedPref.edit();

                    UUID uuid = UUID.randomUUID();

                    String keyName = "h0" + uuid.toString();
                    String keyNumber = "h1" + uuid.toString();
                    String keyInstitution = "h2" + uuid.toString();

                    myEditor.putString(keyName, cardName);
                    Log.i(TAG, "Key (name): " + keyName + ", value: " + cardName);

                    myEditor.putString(keyNumber, cardUID);
                    Log.i(TAG, "Key (#): " + keyNumber + ", value: " + cardUID);

                    myEditor.putString(keyInstitution, String.valueOf(institutionNumber));
                    Log.i(TAG, "Key(Institution): " + keyInstitution + ", value: " + String.valueOf(institutionNumber));

                    myEditor.commit();

                    finish();
                }
            }
        });
    }

    //TODO Get server connection to work.
    private String authenticateWithServer(int institution){
        return null;
    }


    //TODO Load list of institution we support from the server
    private boolean fetchInstitutionList (){

        SharedPreferences mySharedPref = thisInstance.getSharedPreferences("Institutions",0);
        SharedPreferences.Editor myEditor = mySharedPref.edit();

        myEditor.putInt("Aalborg University", 1);
        myEditor.putInt("DTU", 2);
        myEditor.putInt("Fitness World", 3);
        myEditor.commit();


        return false;
    }

}
