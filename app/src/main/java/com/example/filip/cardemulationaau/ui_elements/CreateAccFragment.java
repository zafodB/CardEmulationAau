package com.example.filip.cardemulationaau.ui_elements;

import android.app.Fragment;
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

import com.example.filip.cardemulationaau.R;
import com.example.filip.cardemulationaau.network.LoginToken;
import com.example.filip.cardemulationaau.network.MyRetrofitAPI;
import com.example.filip.cardemulationaau.network.RestService;
import com.example.filip.cardemulationaau.network.User;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.filip.cardemulationaau.ApplicationMain.MEMORY_LOGIN_REF;
import static com.example.filip.cardemulationaau.ApplicationMain.TAG;

/**
 * Created by filip on 23/11/2016.
 */

public class CreateAccFragment extends Fragment {

    EditText emailField;
    EditText passField;
    Button createAccBtn;
    String token;
    String userID;

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

        emailField = (EditText)view.findViewById(R.id.create_acc_email);

        passField = (EditText)view.findViewById(R.id.create_acc_pass);

        createAccBtn = (Button) view.findViewById(R.id.create_acc_btn);

        createAccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                postToServer();
                if (token != null) {
                    SharedPreferences mySharedPref = getActivity().getSharedPreferences(MEMORY_LOGIN_REF, 0);
                    SharedPreferences.Editor edit = mySharedPref.edit();

                    edit.putString("loginToken", token);
                    edit.putString("user_id", userID);
                    edit.commit();

                    MainActivity mActivity = (MainActivity) getActivity();
                    mActivity.replaceFragments();
                }else{
                    Toast.makeText(getActivity(), "Sometihng went wrong, please try again.", Toast.LENGTH_LONG);
                }
            }
        });

        return view;
    }

    void postToServer(){
        MyRetrofitAPI service = RestService.getInstance();
        Call<LoginToken> call = service.createUser(wrapUserData());

        Log.i(TAG,"Enqueing call");
        call.enqueue(new Callback<LoginToken>() {
            @Override
            public void onResponse(Call<LoginToken> call, Response<LoginToken> response) {

                token = response.body().getToken();
                userID = response.body().getUser_id();

                Log.i(TAG, "token is: " + token);
                Log.i(TAG, "user ID is: " + userID);
            }

            @Override
            public void onFailure(Call<LoginToken> call, Throwable t) {
                Log.i(TAG, "Got negative response");
            }
        });
    }

    User wrapUserData(){
        User user = new User();

        user.setEmail(emailField.getText().toString());
        user.setPassword(passField.getText().toString());
        Log.i(TAG, "wrapped data");

        return user;
    }
}
