package com.example.filip.cardemulationaau;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.UUID;

public class AddNewCardActivity extends AppCompatActivity {

    public static final String TAG = "myTag";

    EditText cardNameField;
    EditText cardNumberField;
    Spinner institutionChooser;
    Button addCardButton;

    AddNewCardActivity thisInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_card);

        cardNameField = (EditText) findViewById(R.id.enter_name);
        cardNumberField = (EditText) findViewById(R.id.enter_number);
        institutionChooser = (Spinner) findViewById(R.id.institution_chooser);
        addCardButton = (Button) findViewById(R.id.commit_add);

        thisInstance = this;

        addCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isValid = true;

                String cardName = cardNameField.getText().toString();
                String cardNumber = cardNumberField.getText().toString();
                int institutionNumber = institutionChooser.getSelectedItemPosition();



                if (cardName.isEmpty()){
                    isValid = false;
                    Toast.makeText(getApplicationContext(), getString(R.string.no_card_name_tst),Toast.LENGTH_LONG).show();
                } else if (cardName.length()>30){
                    isValid = false;
                    Toast.makeText(getApplicationContext(), getString(R.string.name_too_long_tst), Toast.LENGTH_LONG).show();
                } else if (cardNumber.isEmpty()){
                    isValid = false;
                    Toast.makeText(getApplicationContext(), getString(R.string.no_card_number_tst), Toast.LENGTH_LONG).show();
                } else if (cardNumber.length() > 50){
                    isValid = false;
                    Toast.makeText(getApplicationContext(), getString(R.string.number_too_long_tst), Toast.LENGTH_LONG).show();
                }

                if (isValid){
                    SharedPreferences mySharedPref = thisInstance.getSharedPreferences("Card Data",0);
                    SharedPreferences.Editor myEditor = mySharedPref.edit();

                    UUID uuid = UUID.randomUUID();

                    String keyName = "h0" + uuid.toString();
                    String keyNumber = "h1" + uuid.toString();
                    String keyInstitution = "h2" + uuid.toString();

                    myEditor.putString(keyName, cardName);
                    myEditor.putString(keyNumber, cardNumber);
                    myEditor.putString(keyInstitution, String.valueOf(institutionNumber));
                    myEditor.commit();

                    finish();
                }

            }
        });
    }

}
