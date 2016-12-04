package com.example.filip.cardemulationaau.ui_elements;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;

import com.example.filip.cardemulationaau.ApplicationMain;
import com.example.filip.cardemulationaau.Constants;
import com.example.filip.cardemulationaau.R;

public class MainActivity extends Activity {

    private static final String FRAGMENT_TAG = "cardFragment";

    FloatingActionButton addCardButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addCardButton = (FloatingActionButton) findViewById(R.id.fab);

        addCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CreateCardActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        ApplicationMain mApplication = (ApplicationMain) getApplication();

        RecyclerViewFragment fragment = (RecyclerViewFragment) getFragmentManager().findFragmentByTag(FRAGMENT_TAG);

        if (mApplication.getId() == null) {
            getFragmentManager().beginTransaction().add(R.id.fragment_placeholder, CreateAccFragment.newInstance())
                    .commit();
            addCardButton.setVisibility(View.INVISIBLE);

        } else if (mApplication.getToken() == null) {
            Log.i(Constants.TAG, "Not implemented yet.");
            //TODO login screen to be implemented here

        } else if (fragment == null) {
            getFragmentManager().beginTransaction().add(R.id.fragment_placeholder, RecyclerViewFragment.newInstance()
                    , FRAGMENT_TAG)
                    .commit();

        } else {
            fragment.notifyAdapter();
        }
        super.onResume();
    }

    void replaceFragments() {
        getFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, RecyclerViewFragment.newInstance()
        ).commit();
        addCardButton.setVisibility(View.VISIBLE);
    }

}
