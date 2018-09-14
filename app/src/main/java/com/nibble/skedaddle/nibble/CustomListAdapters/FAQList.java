package com.nibble.skedaddle.nibble.CustomListAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nibble.skedaddle.nibble.CustomModels.FAQModel;
import com.nibble.skedaddle.nibble.CustomModels.ReviewModel;
import com.nibble.skedaddle.nibble.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Chris on 2018/09/14.
 */

public class FAQList extends ArrayAdapter<FAQModel> {

    private ArrayList<FAQModel> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtQuestion;
        TextView txtAnswer;


    }

    public FAQList(ArrayList<FAQModel> data, Context context) {
        super(context, R.layout.custom_faqlistitem, data);
        this.dataSet = data;
        this.mContext=context;

    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        FAQModel dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        FAQList.ViewHolder viewHolder; // view lookup cache stored in tag


        viewHolder = new FAQList.ViewHolder();
        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.custom_faqlistitem, parent, false);
        viewHolder.txtQuestion = (TextView) convertView.findViewById(R.id.txtQuestion);
        viewHolder.txtAnswer = (TextView) convertView.findViewById(R.id.txtAnswer);


        viewHolder.txtQuestion.setText(dataModel.getQuestion());
        viewHolder.txtAnswer.setText(dataModel.getAnswer());

        // Return the completed view to render on screen
        return convertView;
    }

}
