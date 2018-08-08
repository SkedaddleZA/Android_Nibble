package com.nibble.skedaddle.nibble.CustomListAdapters;

/**
 * Created by Chris on 2018/07/31.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nibble.skedaddle.nibble.CustomModels.BookingModel;
import com.nibble.skedaddle.nibble.CustomModels.RestaurantModel;
import com.nibble.skedaddle.nibble.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class BookList extends ArrayAdapter<BookingModel> {

    private ArrayList<BookingModel> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtRestaurantName;
        TextView txtDate;
        TextView txtGuests;
        TextView txtTime;
        ImageView imgStatus;
    }

    public BookList(ArrayList<BookingModel> data, Context context) {
        super(context, R.layout.custom_booklistitem, data);
        this.dataSet = data;
        this.mContext=context;

    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        BookingModel dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag


        viewHolder = new ViewHolder();
        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.custom_booklistitem, parent, false);
        viewHolder.txtRestaurantName = (TextView) convertView.findViewById(R.id.restaurantname);
        viewHolder.txtDate = (TextView) convertView.findViewById(R.id.date);
        viewHolder.txtGuests = (TextView) convertView.findViewById(R.id.guests);
        viewHolder.txtTime = (TextView) convertView.findViewById(R.id.time);
        viewHolder.imgStatus = (ImageView) convertView.findViewById(R.id.ivStatus);


        viewHolder.txtRestaurantName.setText(dataModel.getRestaurantName());
        viewHolder.txtDate.setText(dataModel.getDate());
        viewHolder.txtGuests.setText(dataModel.getGuests());
        viewHolder.txtTime.setText(dataModel.getTime());
        String status = dataModel.getStatus();

        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("accept", R.drawable.accept);
        map.put("decline", R.drawable.decline);
        map.put("pending", R.drawable.pending);

        switch(status){
            case "Y": viewHolder.imgStatus.setImageResource(map.get("accept")); break;
            case "N": viewHolder.imgStatus.setImageResource(map.get("decline")); break;
            case "P": viewHolder.imgStatus.setImageResource(map.get("pending")); break;
        }

        // Return the completed view to render on screen
        return convertView;
    }
}