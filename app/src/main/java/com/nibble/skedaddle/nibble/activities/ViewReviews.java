package com.nibble.skedaddle.nibble.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.nibble.skedaddle.nibble.R;

public class ViewReviews extends AppCompatActivity {

    private Button bAddReview;

    private String[] restaurantfulldetails, customerdetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reviews);

        final Intent restaurantintent = getIntent();
        restaurantfulldetails = restaurantintent.getStringArrayExtra("restaurantdetails");
        customerdetails = restaurantintent.getStringArrayExtra("customerdetails");

        bAddReview=findViewById(R.id.bAddReview);

        bAddReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addReview = new Intent(ViewReviews.this, AddReview.class);
                addReview.putExtra("customerdetails", customerdetails);
                addReview.putExtra("restaurantdetails", restaurantfulldetails);
                ViewReviews.this.startActivity(addReview);

            }

        });



    }
}
