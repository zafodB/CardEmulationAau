package com.example.filip.cardemulationaau.ui_elements;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.filip.cardemulationaau.ApplicationMain;
import com.example.filip.cardemulationaau.Constants;
import com.example.filip.cardemulationaau.R;
import com.example.filip.cardemulationaau.network.CardForServer;
import com.example.filip.cardemulationaau.network.CardForUser;
import com.example.filip.cardemulationaau.network.MyRetrofitAPI;
import com.example.filip.cardemulationaau.network.RestService;

import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateCardActivity extends AppCompatActivity {

    EditText emailField;
    EditText pinField;
    Spinner institutionChooser;
    Button createCardButton;
    ProgressDialog dialog;

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
        setUpLoadingDialog();

        createCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(Constants.TAG, "The dialog is: " + String.valueOf(dialog.isShowing()));
                fetchInstitutionList();

                String emailInst = emailField.getText().toString();
                int pin = Integer.parseInt(pinField.getText().toString());
                int institutionNumber = institutionChooser.getSelectedItemPosition();

                if (validateData(emailInst)) {
                    dialog.show();
                    ApplicationMain mApplication = (ApplicationMain) getApplication();
                    authenticateWithServer(mApplication.getToken(), institutionNumber, emailInst, pin);
                } else {
                    Log.i(Constants.TAG, "User entered invalid data.");
                }
            }
        });
    }

    /**
     * Authenticate entered card data with the server.
     *
     * @param token       Login token of Current session - not part of UI
     * @param institution Institution chosen by user from the list. List should be dynamically loaded (on TO-DO list)
     * @param email       Email registered with institution - entered by user
     * @param pin         One-time pin. Entered by user, supplied by institution
     */
    private void authenticateWithServer(String token, final int institution, final String email, int pin) {
        MyRetrofitAPI service = RestService.getInstance();

        //TODO remake so route is not hardcoded
        Call<CardForUser> call = service.createCard(Constants.BEARER + token, "aau", wrapCardData(institution, email,
                pin));

        call.enqueue(new Callback<CardForUser>() {
            @Override
            public void onResponse(Call<CardForUser> call, Response<CardForUser> response) {
                String cardNumber = response.body().getCard_id();

                saveCardsToMemory(email, institution, cardNumber);
                Log.i(Constants.TAG, "Card number is: " + cardNumber);

                dialog.hide();
                finish();
            }

            @Override
            public void onFailure(Call<CardForUser> call, Throwable t) {
                Log.i(Constants.TAG, "Got negative response");
                Log.i(Constants.TAG, "The error is: " + t.getMessage());

                Toast.makeText(getApplicationContext(), getString(R.string.tst_server_error), Toast.LENGTH_SHORT).show();
                dialog.hide();
            }
        });

    }

    /**
     * Prepare card data into CardForServer class used by GSON converter.
     */
    CardForServer wrapCardData(int institution, String email, int pin) {
        CardForServer card = new CardForServer();
        card.setInstitution(institution);
        card.setEmail(email);
        card.setPin(pin);

        return card;
    }

    /**
     * Save cards to SharedPreferences memory, under MEMORY_CARDS_REFERENCE.
     * <p>
     * An unique ID is generated for triplet of data. Keys are generated using the unique ID and prefix h0 - h2. Data
     * are saved as corresponding values to this keys.
     */
    void saveCardsToMemory(String email, int instNumber, String cardNumber) {
        SharedPreferences mySharedPref = thisInstance.getSharedPreferences(Constants.MEMORY_CARDS_REF, 0);
        SharedPreferences.Editor myEditor = mySharedPref.edit();

        UUID uuid = UUID.randomUUID();

        String keyName = "h0" + uuid.toString();
        String keyNumber = "h1" + uuid.toString();
        String keyInstitution = "h2" + uuid.toString();

        myEditor.putString(keyName, email);
        myEditor.putString(keyNumber, cardNumber);
        myEditor.putString(keyInstitution, String.valueOf(instNumber));

        //TODO fix institutions' names (so they're not hardcoded)

        myEditor.commit();
    }

    //TODO Load list of institution, we support, from the server
    private void fetchInstitutionList() {

        SharedPreferences mySharedPref = thisInstance.getSharedPreferences("Institutions", 0);
        SharedPreferences.Editor myEditor = mySharedPref.edit();

        myEditor.putInt("Aalborg University", 1);
        myEditor.putInt("DTU", 2);
        myEditor.putInt("Fitness World", 3);
        myEditor.commit();
    }

    /**
     * Sets up properties of Loading dialog that is displayed while communication with server is ongoing.
     */
    private void setUpLoadingDialog() {
        dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(getString(R.string.loading_msg));
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);
    }

    /**
     * Simple method to validate, whether user-entered data are in proper format.
     * @param email Email entered by user
     * @return False, if any property is broken, true otherwise.
     */
    boolean validateData(String email) {
        if (email.isEmpty()) {
            Toast.makeText(getApplication(), getString(R.string.tst_email_empty), Toast.LENGTH_LONG).show();
            return false;
        } else if (email.length() > 80) {
            Toast.makeText(getApplication(), getString(R.string.tst_email_long), Toast.LENGTH_SHORT).show();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(getApplication(), getString(R.string.tst_email_invalid), Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
