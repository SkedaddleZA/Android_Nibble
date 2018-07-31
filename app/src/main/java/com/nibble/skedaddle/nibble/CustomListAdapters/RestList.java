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

import com.nibble.skedaddle.nibble.CustomModels.RestaurantModel;
import com.nibble.skedaddle.nibble.R;

import java.util.ArrayList;


public class RestList extends ArrayAdapter<RestaurantModel> {

    private ArrayList<RestaurantModel> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
        TextView txtPrice;
    }

    public RestList(ArrayList<RestaurantModel> data, Context context) {
        super(context, R.layout.custom_restlistrowitem, data);
        this.dataSet = data;
        this.mContext=context;

    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        RestaurantModel dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag


        viewHolder = new ViewHolder();
        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.custom_restlistrowitem, parent, false);
        viewHolder.txtName = (TextView) convertView.findViewById(R.id.name);
        viewHolder.txtPrice = (TextView) convertView.findViewById(R.id.suburb);


        viewHolder.txtName.setText(dataModel.getName());
        viewHolder.txtPrice.setText(dataModel.getSuburb());

        // Return the completed view to render on screen
        return convertView;
    }
}