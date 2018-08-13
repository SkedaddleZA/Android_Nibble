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
import com.nibble.skedaddle.nibble.CustomModels.ReviewModel;
import com.nibble.skedaddle.nibble.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ReviewList extends ArrayAdapter<ReviewModel> {

    private ArrayList<ReviewModel> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtBody;
        TextView txtDate;
        TextView txtAuthor;
        ImageView star1;
        ImageView star2;
        ImageView star3;
        ImageView star4;
        ImageView star5;
    }

    public ReviewList(ArrayList<ReviewModel> data, Context context) {
        super(context, R.layout.custom_reviewlistitem, data);
        this.dataSet = data;
        this.mContext=context;

    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ReviewModel dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag


        viewHolder = new ViewHolder();
        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.custom_reviewlistitem, parent, false);
        viewHolder.txtBody = (TextView) convertView.findViewById(R.id.body);
        viewHolder.txtDate = (TextView) convertView.findViewById(R.id.date);
        viewHolder.txtAuthor = (TextView) convertView.findViewById(R.id.author);
        viewHolder.star1 = (ImageView) convertView.findViewById(R.id.star1);
        viewHolder.star2 = (ImageView) convertView.findViewById(R.id.star2);
        viewHolder.star3 = (ImageView) convertView.findViewById(R.id.star3);
        viewHolder.star4 = (ImageView) convertView.findViewById(R.id.star4);
        viewHolder.star5 = (ImageView) convertView.findViewById(R.id.star5);


        viewHolder.txtBody.setText(dataModel.getBody());
        viewHolder.txtDate.setText(dataModel.getDate());
        viewHolder.txtAuthor.setText(dataModel.getAuthor());
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