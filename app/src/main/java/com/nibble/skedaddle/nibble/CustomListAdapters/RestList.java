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

import com.nibble.skedaddle.nibble.CustomModels.RestaurantModel;
import com.nibble.skedaddle.nibble.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class RestList extends ArrayAdapter<RestaurantModel> {

    private ArrayList<RestaurantModel> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
        TextView txtPrice;
        ImageView star1;
        ImageView star2;
        ImageView star3;
        ImageView star4;
        ImageView star5;
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
        viewHolder.star1 = (ImageView) convertView.findViewById(R.id.star1);
        viewHolder.star2 = (ImageView) convertView.findViewById(R.id.star2);
        viewHolder.star3 = (ImageView) convertView.findViewById(R.id.star3);
        viewHolder.star4 = (ImageView) convertView.findViewById(R.id.star4);
        viewHolder.star5 = (ImageView) convertView.findViewById(R.id.star5);


        viewHolder.txtName.setText(dataModel.getName());
        viewHolder.txtPrice.setText(dataModel.getSuburb());
        String rating = dataModel.getRating();

        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("star", R.drawable.star);
        map.put("nostar", R.drawable.nostar);

        switch(rating){
            case "1":
                viewHolder.star1.setImageResource(map.get("star"));
                viewHolder.star2.setImageResource(map.get("nostar"));
                viewHolder.star3.setImageResource(map.get("nostar"));
                viewHolder.star4.setImageResource(map.get("nostar"));
                viewHolder.star5.setImageResource(map.get("nostar"));
                break;
            case "2":
                viewHolder.star1.setImageResource(map.get("star"));
                viewHolder.star2.setImageResource(map.get("star"));
                viewHolder.star3.setImageResource(map.get("nostar"));
                viewHolder.star4.setImageResource(map.get("nostar"));
                viewHolder.star5.setImageResource(map.get("nostar"));
                break;
            case "3":
                viewHolder.star1.setImageResource(map.get("star"));
                viewHolder.star2.setImageResource(map.get("star"));
                viewHolder.star3.setImageResource(map.get("star"));
                viewHolder.star4.setImageResource(map.get("nostar"));
                viewHolder.star5.setImageResource(map.get("nostar"));
                break;
            case "4":
                viewHolder.star1.setImageResource(map.get("star"));
                viewHolder.star2.setImageResource(map.get("star"));
                viewHolder.star3.setImageResource(map.get("star"));
                viewHolder.star4.setImageResource(map.get("star"));
                viewHolder.star5.setImageResource(map.get("nostar"));
                break;
            case "5":
                viewHolder.star1.setImageResource(map.get("star"));
                viewHolder.star2.setImageResource(map.get("star"));
                viewHolder.star3.setImageResource(map.get("star"));
                viewHolder.star4.setImageResource(map.get("star"));
                viewHolder.star5.setImageResource(map.get("star"));
                break;
        }

        // Return the completed view to render on screen
        return convertView;
    }
}