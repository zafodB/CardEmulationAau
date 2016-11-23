package com.example.filip.cardemulationaau.ui_elements;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import com.example.filip.cardemulationaau.ApplicationMain;
import com.example.filip.cardemulationaau.R;

public class MainActivity extends Activity {

    public static final String TAG = "myTag";
    static final String FRAGMENT_TAG = "cardFragment";

    FloatingActionButton addCardButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ApplicationMain mApplication = (ApplicationMain) getApplication();

        if (mApplication.getId() == null) {
            getFragmentManager().beginTransaction().add(R.id.fragment_placeholder, CreateAccFragment.newInstance()).commit();
        } else {
            getFragmentManager().beginTransaction().add(R.id.fragment_placeholder, RecyclerViewFragment.newInstance(), FRAGMENT_TAG)
                    .commit();
        }

        addCardButton = (FloatingActionButton) findViewById(R.id.fab);

        addCardButton.setVisibility(View.INVISIBLE);
        addCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CreateCardActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        RecyclerViewFragment fragment = (RecyclerViewFragment) getFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        if (fragment != null) {
            fragment.notifyAdapter();
        }
        super.onResume();
    }

    void replaceFragments(){
        getFragmentManager().beginTransaction().replace(R.id.fragment_placeholder,RecyclerViewFragment.newInstance()).commit();
    }

}
