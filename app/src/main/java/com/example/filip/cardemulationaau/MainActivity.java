package com.example.filip.cardemulationaau;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    public static final boolean WIPE_MEMORY = true;

    public static final String TAG = "myTag";
    static final String FRAGMENT_TAG = "cardFragment";

    FloatingActionButton addCardButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (WIPE_MEMORY) {
            SharedPreferences mySharedPref = this.getSharedPreferences("Card Data", 0);
            SharedPreferences.Editor myEditor = mySharedPref.edit();
            myEditor.clear().commit();
        }

        getFragmentManager().beginTransaction().add(R.id.fragment, RecyclerViewFragment.newInstance(), FRAGMENT_TAG)
                .commit();

        addCardButton = (FloatingActionButton) findViewById(R.id.fab);

        addCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddNewCardActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {

        RecyclerViewFragment fragment = (RecyclerViewFragment) getFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        Log.i(TAG, "tried to notify Fragment");
        fragment.notifyAdapter();

        super.onResume();
    }
}
