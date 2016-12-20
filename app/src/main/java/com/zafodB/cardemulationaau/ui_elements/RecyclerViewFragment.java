package com.zafodB.cardemulationaau.ui_elements;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zafodB.cardemulationaau.Constants;
import com.zafodB.cardemulationaau.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by filip on 18/11/2016.
 */

public class RecyclerViewFragment extends Fragment {

    LinearLayoutManager mLayoutManager;
    CustomAdapter myAdapter;
    RecyclerView mRecyclerView;

    TextView mTextView;

    public static RecyclerViewFragment newInstance() {
        RecyclerViewFragment fragment = new RecyclerViewFragment();
        Log.i(Constants.TAG, "fragment instance created");
        return fragment;
    }

    public RecyclerViewFragment() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recycler_view, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rec_view_holder);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        List<LinkedHashMap<String, ?>> mList = getCardData();

        myAdapter = new CustomAdapter(mList);
        mRecyclerView.setAdapter(myAdapter);

        mTextView = (TextView) getActivity().findViewById(R.id.no_card_hint_view);
        updateHintVisibility(mList.isEmpty());

        return rootView;
    }

    /**
     * Simple method that calls build in "notifyDatasetChanged()" method of the RecyclerView.
     */
    public void notifyAdapter() {
        List<LinkedHashMap<String, ?>> mList = getCardData();

        myAdapter.putCardData(mList);
        updateHintVisibility(mList.isEmpty());

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * Method used to read data from SharedPreferences storage (key-value pair based). This method prepares the data
     * for later usage by putting them into List of LinkedHashMaps, where each entry of the list is a map of 3 items:
     * id, name and institution.
     * <p>
     * The data in the memory are stored in random order, but are bound together by same UUID. This method creates
     * intermediate list of UUIDs and their placement int the final list. Whenever value from the memory is read, the
     * UUID's placement in the list is looked up and the valuse is saved to corresponding place in the list.
     *
     * @return Returned list, after processing.
     */
    List<LinkedHashMap<String, ?>> getCardData() {
        SharedPreferences mySharedPref = getActivity().getSharedPreferences(Constants.MEMORY_CARDS_REF, 0);
        Map<String, ?> dataMap = mySharedPref.getAll();

        /* Helper map to check, if UUID already was assigned to a number in the list. */
        LinkedHashMap<String, Integer> helperMap = new LinkedHashMap<>();

        /* Helper list to store all data in temporarily. */
        List<String> helperList = new ArrayList<>();

        int count = 0;

        for (int i = 0; i < dataMap.entrySet().size(); i++) {
            helperList.add(i, "");
        }

        /* Process each line of data from memory separately, using this for loop */
        for (Map.Entry<String, ?> entry : dataMap.entrySet()) {
            String key = entry.getKey();
            String uuid = key.substring(2);

            boolean alreadyAssigned = false;
            int helpPos = 0;

           /* Check if UUID was already assigned place in final list. */
            for (Map.Entry<String, Integer> e : helperMap.entrySet()) {
                if (e.getKey().equals(uuid)) {
                    alreadyAssigned = true;
                    helpPos = e.getValue();
                    break;
                }
                helpPos++;
            }

            /* If not, assign UUID place in final list. */
            if (!alreadyAssigned) {
                helperMap.put(uuid, count);
                helpPos = count;
                count++;
            }

          /* Order data to temporary list */
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

        /* Get data from temporary list to Output list */
        for (int i = helperList.size() - 1; i >= 0; i = i - 3) {
            LinkedHashMap<String, String> myMap = new LinkedHashMap<>();
            myMap.put("name", helperList.get(i - 2));
            myMap.put("id", helperList.get(i - 1));
            myMap.put("institution", helperList.get(i));

            dataList.add(myMap);
        }

        return dataList;
    }

    /**
     * Simple method to show or hide the hint on the default screen
     *
     * @param visible switch of the visibility.
     */
    void updateHintVisibility(boolean visible) {
        if (visible) {
            mTextView.setVisibility(View.VISIBLE);
        } else {
            mTextView.setVisibility(View.INVISIBLE);
        }
    }
}
