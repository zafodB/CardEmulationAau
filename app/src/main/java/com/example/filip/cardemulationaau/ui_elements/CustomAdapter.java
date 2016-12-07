package com.example.filip.cardemulationaau.ui_elements;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.filip.cardemulationaau.Constants;
import com.example.filip.cardemulationaau.R;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by filip on 18/11/2016.
 */

 class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    TextView cardName;
    TextView cardId;
    ImageView cardThumb;
    MyViewHolder mViewHolder;

    List<LinkedHashMap<String, ?>> listOfData;
    LinkedHashMap<String,?> tempMap;

    public CustomAdapter (List<LinkedHashMap<String,?>> initialData){
        tempMap = new LinkedHashMap<>();
        listOfData = initialData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_card_layout, parent, false);
        mViewHolder = new MyViewHolder(myView);
        mViewHolder.setIsRecyclable(false);

        return  mViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        tempMap = listOfData.get(position);

        cardName.setText(tempMap.get("name").toString());
        cardId.setText(tempMap.get("id").toString());
    }

    @Override
    public int getItemCount() {
        if (listOfData == null){
            return 0;
        } else {
            return listOfData.size();
        }
    }

    void putCardData(List<LinkedHashMap<String, ?>> data){
        if (data == null){
            Log.i(Constants.TAG, "Data is null.");
        } else {
            listOfData = data;
        }
    }

     class MyViewHolder extends RecyclerView.ViewHolder{

        MyViewHolder(View itemView) {
            super(itemView);

            cardName = (TextView) itemView.findViewById(R.id.card_name);
            cardId = (TextView) itemView.findViewById(R.id.card_id);
            cardThumb = (ImageView) itemView.findViewById(R.id.card_logo);

        }
    }
}
