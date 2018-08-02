package com.nibble.skedaddle.nibble.CustomListAdapters;

/**
 * Created by Chris on 2018/07/31.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nibble.skedaddle.nibble.CustomModels.BookingModel;
import com.nibble.skedaddle.nibble.CustomModels.RestaurantModel;
import com.nibble.skedaddle.nibble.R;

import java.util.ArrayList;


public class BookList extends ArrayAdapter<BookingModel> {

    private ArrayList<BookingModel> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtRestaurantName;
        TextView txtDate;
        TextView txtGuests;
        TextView txtTime;
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


        viewHolder.txtRestaurantName.setText(dataModel.getRestaurantName());
        viewHolder.txtDate.setText(dataModel.getDate());
        viewHolder.txtGuests.setText(dataModel.getGuests());
        viewHolder.txtTime.setText(dataModel.getTime());

        // Return the completed view to render on screen
        return convertView;
    }
}