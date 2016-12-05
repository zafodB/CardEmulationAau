package com.example.filip.cardemulationaau.ui_elements;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.filip.cardemulationaau.Constants;
import com.example.filip.cardemulationaau.R;
import com.example.filip.cardemulationaau.network.LoginToken;
import com.example.filip.cardemulationaau.network.MyRetrofitAPI;
import com.example.filip.cardemulationaau.network.RestService;
import com.example.filip.cardemulationaau.network.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.filip.cardemulationaau.Constants.MEMORY_LOGIN_REF;
import static com.example.filip.cardemulationaau.Constants.TAG;

/**
 * Created by filip on 23/11/2016.
 */

public class CreateAccFragment extends Fragment {

    EditText emailField;
    EditText passField;
    Button createAccBtn;
    String token;
    String userID;
    ProgressDialog dialog;

    public static CreateAccFragment newInstance() {
        CreateAccFragment fragment = new CreateAccFragment();
        Log.i(TAG, "fragment instance created");
        return fragment;
    }

    public CreateAccFragment() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_create_acc, container, false);

        emailField = (EditText) view.findViewById(R.id.create_acc_email);
        passField = (EditText) view.findViewById(R.id.create_acc_pass);
        createAccBtn = (Button) view.findViewById(R.id.create_acc_btn);
        setUpLoadingDialog();

        //TODO implement data validation here
        //TODO work on the UI little-bit (make it prettier)

        createAccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                authenticateWithServer();
            }
        });

        return view;
    }

    /**
     * Sends the user entered data into server. Expects login token as a response.
     */
    void authenticateWithServer() {
        MyRetrofitAPI service = RestService.getInstance();
        Call<LoginToken> call = service.createUser(wrapUserData());

        Log.i(TAG, "Enqueing call");
        call.enqueue(new Callback<LoginToken>() {
            @Override
            public void onResponse(Call<LoginToken> call, Response<LoginToken> response) {

                token = response.body().getToken();
                userID = response.body().getUser_id();

                Log.i(TAG, "token is: " + token);
                Log.i(TAG, "user ID is: " + userID);

                saveData();
                dialog.hide();
            }

            @Override
            public void onFailure(Call<LoginToken> call, Throwable t) {
                Log.i(TAG, "Got negative response");
                dialog.hide();
                Toast.makeText(getActivity(), "Something went wrong, please try again.", Toast.LENGTH_LONG).show();

            }
        });
    }

    /**
     * Prepares data into the User class, used by GSON convereter.
     */
    User wrapUserData() {
        User user = new User();

        user.setEmail(emailField.getText().toString());
        user.setPassword(passField.getText().toString());
        Log.i(TAG, "wrapped data");

        return user;
    }

    /**
     * Saves the token and userID into SharedPreferences memory, if the token is not null.
     */
    void saveData() {
        if (token != null) {
            SharedPreferences mySharedPref = getActivity().getSharedPreferences(MEMORY_LOGIN_REF, 0);
            SharedPreferences.Editor edit = mySharedPref.edit();

            edit.putString(Constants.MEMORY_TOKEN_REF, token);
            edit.putString(Constants.MEMORY_USER_ID_REF, userID);
            edit.commit();

            updateUI();
        } else {
            Log.i(TAG, "Something wrong with token.");
        }
    }

    /**
     * Updates UI and calls to change fragments.
     */
    void updateUI() {
        dialog.hide();
        MainActivity mActivity = (MainActivity) getActivity();
        mActivity.replaceFragments();
    }

    /**
     * Sets up proparties of a loading dialog that is displayed while communicating with server.
     */
    private void setUpLoadingDialog() {
        dialog = new ProgressDialog(getActivity());
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(getString(R.string.loading_msg));
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);
    }
}
