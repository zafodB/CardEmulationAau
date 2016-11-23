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

import com.example.filip.cardemulationaau.R;

import java.util.UUID;

import static com.example.filip.cardemulationaau.ApplicationMain.TAG;

/**
 * Created by filip on 23/11/2016.
 */

public class CreateAccFragment extends Fragment {

    EditText emailField;
    Button createAccBtn;

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
        createAccBtn = (Button) view.findViewById(R.id.create_acc_btn);

        createAccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO Server connection goes here

                SharedPreferences mySharedPref = getActivity().getSharedPreferences("ID_storage", 0);
                SharedPreferences.Editor edit = mySharedPref.edit();

                UUID id = UUID.randomUUID();

                edit.putString("id", id.toString());
                edit.commit();

                MainActivity mActivity = (MainActivity) getActivity();
                mActivity.replaceFragments();
            }
        });


        return view;
    }
}
