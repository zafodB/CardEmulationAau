package com.example.filip.cardemulationaau;

import android.support.v7.widget.RecyclerView;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by filip on 18/11/2016.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    public static final String TAG = "myTag";


    TextView cardName;
    TextView cardId;
    ImageView cardThumb;


    List<Map<String, ?>> listOfData;
    Map<String,?> tempMap;

    public CustomAdapter (List<Map<String,?>> initialData){
        tempMap = new ArrayMap<>();
        listOfData = initialData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View myView = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_card_layout, parent, false);

        return new MyViewHolder(myView);
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
            Log.i(TAG, "Outputed number of data is: " + String.valueOf(listOfData.size()));
            return listOfData.size();
        }
    }

    void putCardData(List<Map<String, ?>> data){

        if (data == null){
            Log.i(TAG, "Data is null.");
        } else {
            listOfData = data;
            Log.i(TAG, "Data has been put");
//            this.notifyDataSetChanged();
        }
    }


     class MyViewHolder extends RecyclerView.ViewHolder{

        public MyViewHolder(View itemView) {
            super(itemView);

            cardName = (TextView) itemView.findViewById(R.id.card_name);
            cardId = (TextView) itemView.findViewById(R.id.card_id);
            cardThumb = (ImageView) itemView.findViewById(R.id.card_logo);

        }

    }
}
