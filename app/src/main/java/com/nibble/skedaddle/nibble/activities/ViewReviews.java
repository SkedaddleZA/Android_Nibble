package com.nibble.skedaddle.nibble.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nibble.skedaddle.nibble.R;

public class ViewReviews extends AppCompatActivity {


    private String[] restaurantfulldetails, customerdetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reviews);

        final Intent restaurantintent = getIntent();
        restaurantfulldetails = restaurantintent.getStringArrayExtra("restaurantdetails");
        customerdetails = restaurantintent.getStringArrayExtra("customerdetails");


    }
}
