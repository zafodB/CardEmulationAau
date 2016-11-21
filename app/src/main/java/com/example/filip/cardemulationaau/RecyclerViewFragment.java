package com.example.filip.cardemulationaau;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by filip on 18/11/2016.
 */

public class RecyclerViewFragment extends Fragment {

    public static final String TAG = "myTag";

    //    CardView mCardView;
    LinearLayoutManager mLayoutManager;

    CustomAdapter myAdapter;
    RecyclerView mRecyclerView;

    public static RecyclerViewFragment newInstance() {
        RecyclerViewFragment fragment = new RecyclerViewFragment();
        Log.i(TAG, "fragment instance created");
        return fragment;
    }

    public RecyclerViewFragment() {
        super();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.recycler_view_fragment, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rec_view_holder);

        mLayoutManager = new LinearLayoutManager(getActivity());

        mRecyclerView.setLayoutManager(mLayoutManager);

        myAdapter = new CustomAdapter(getCardData());

        mRecyclerView.setAdapter(myAdapter);

        return rootView;
    }

    public void notifyAdapter() {
        myAdapter.putCardData(getCardData());

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myAdapter.notifyDataSetChanged();
            }
        });
    }

    List<LinkedHashMap<String, ?>> getCardData() {
        SharedPreferences mySharedPref = getActivity().getSharedPreferences("Card Data", 0);
        Map<String, ?> dataMap = mySharedPref.getAll();

//        Helper map to check, if UUID already was assigned to a number in the list.
        LinkedHashMap<String, Integer> helperMap = new LinkedHashMap<>();

//        Helper list to store all data in temporarily.
        List<String> helperList = new ArrayList<>();

        int count = 0;

        for (int i = 0 ; i < dataMap.entrySet().size() ; i++){
            helperList.add(i, "");
        }

//        Process each line of data from memory separately, using this for loop
        for (Map.Entry<String, ?> entry : dataMap.entrySet()) {
            String key = entry.getKey();
            Log.i(TAG, "Key is: " + key);
            String uuid = key.substring(2);

            boolean alreadyAssigned = false;
            int helpPos = 0;

//            Check if UUID was already assigned place in final list.
            for (Map.Entry<String, Integer> e : helperMap.entrySet()) {
                if (e.getKey().equals(uuid)) {
                    alreadyAssigned = true;
                    helpPos = e.getValue();
                    break;
                }
                helpPos++;
            }

//            If not, assign UUID place in final list.
            if (!alreadyAssigned) {
//                helpPos++;
                helperMap.put(uuid, count);
                helpPos = count;
                count++;
            }

//          Order data to temporary list
            if (key.charAt(0) == 'h' && key.charAt(1) == '0') {
                helperList.remove(helpPos * 3);
                helperList.add(helpPos * 3, entry.getValue().toString());
            } else if (key.charAt(0) == 'h' && key.charAt(1) == '1') {
                helperList.remove(helpPos * 3 + 1);
                helperList.add(helpPos * 3 + 1, entry.getValue().toString());
            } else if (key.charAt(0) == 'h' && key.charAt(1) == '2') {
                helperList.remove(helpPos * 3 + 2);
                helperList.add(helpPos * 3 + 2, entry.getValue().toString());
            }
        }

        List<LinkedHashMap<String, ?>> dataList = new ArrayList<>();

//        Get data from temporary list to Output list
        for (int i = helperList.size() - 1; i >= 0; i = i - 3) {
            LinkedHashMap<String, String> myMap = new LinkedHashMap<>();
            myMap.put("name", helperList.get(i - 2));
//            Log.i(TAG,"Put this name: " + helperList.get(i-2) + ". From this place: " + String.valueOf(i-2) );
            myMap.put("id", helperList.get(i - 1));
//            Log.i(TAG,"Put this id: " + helperList.get(i-1) + ". From this place: " + String.valueOf(i-1) );
            myMap.put("institution", helperList.get(i));
//            Log.i(TAG,"Put this institution: " + helperList.get(i) + ". From this place: " + String.valueOf(i) );

            dataList.add(myMap);
        }

//        Log.i(TAG, "The length of data is: " + String.valueOf(count));
//        Log.i(TAG,"");

        return dataList;
    }

}
