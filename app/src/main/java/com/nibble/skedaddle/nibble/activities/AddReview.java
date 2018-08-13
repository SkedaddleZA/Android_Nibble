package com.nibble.skedaddle.nibble.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nibble.skedaddle.nibble.R;

public class AddReview extends AppCompatActivity {

    private String[] restaurantfulldetails, customerdetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);

        final Intent addReview = getIntent();
        restaurantfulldetails = addReview.getStringArrayExtra("restaurantdetails");
        customerdetails = addReview.getStringArrayExtra("customerdetails");
    }
}
