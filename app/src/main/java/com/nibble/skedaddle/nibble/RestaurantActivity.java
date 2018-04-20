package com.nibble.skedaddle.nibble;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RestaurantActivity extends AppCompatActivity {

    private TextView tvRestaurantDetails;
    private Button bRBooking;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        final Intent restaurant = getIntent();
        final String[] restaurantdetails = restaurant.getStringArrayExtra("restaurantdetails");
        bRBooking = findViewById(R.id.bRBooking);
        tvRestaurantDetails = findViewById(R.id.tvRestaurantDetails);

        tvRestaurantDetails.setText(restaurantdetails[0] + " " + restaurantdetails[1] + " " + restaurantdetails[2]);



        bRBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rbooking = new Intent(RestaurantActivity.this, BookingActivity.class);
                rbooking.putExtra("restaurantdetails",restaurantdetails);
                RestaurantActivity.this.startActivity(rbooking);
            }
        });

    }
}
