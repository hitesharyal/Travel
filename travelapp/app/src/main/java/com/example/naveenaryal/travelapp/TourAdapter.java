package com.example.naveenaryal.travelapp;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Naveen Aryal on 1/7/2018.
 */

public class TourAdapter extends ArrayAdapter<TourPkg> {

    public TourAdapter(Activity context, ArrayList<TourPkg> pak){
        super(context,0,pak);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemsView=convertView;
        if(listItemsView == null){
            listItemsView= LayoutInflater.from(getContext()).inflate(R.layout.list_view,parent,false);
        }
        TourPkg current= (TourPkg) getItem(position);
        TextView place= (TextView) listItemsView.findViewById(R.id.place);
        place.setText(current.place);

        TextView duration= (TextView) listItemsView.findViewById(R.id.duration);
        duration.setText(current.duration);
        ImageView imageView= (ImageView) listItemsView.findViewById(R.id.imaget);
        imageView.setImageResource(current.imaget);

        TextView price = listItemsView.findViewById(R.id.price);
        price.setText(current.price);

        return listItemsView;



    }
}
