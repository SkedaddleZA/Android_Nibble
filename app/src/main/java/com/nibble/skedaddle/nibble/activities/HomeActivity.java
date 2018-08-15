package com.nibble.skedaddle.nibble.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nibble.skedaddle.nibble.R;

import org.w3c.dom.Text;

public class HomeActivity extends AppCompatActivity {//my naam tollie

    private RelativeLayout bHome, bProfile, bBookings, rlRestaurant, rlFood, rlLocation;
    private TextView tvDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        Intent Home = getIntent();
        final String[] customerdetails = Home.getStringArrayExtra("customerdetails");


        bProfile = findViewById(R.id.bRProfile);
        bBookings = findViewById(R.id.bBookings);
        tvDetails = findViewById(R.id.tvDetails);
        tvDetails.setText("Signed in as " + customerdetails[1] + " " + customerdetails[2]);
        rlRestaurant = findViewById(R.id.rlRestaurant);
        rlFood = findViewById(R.id.rlFood);
        rlLocation = findViewById(R.id.rlLocation);

        rlRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent restaurantSearch = new Intent(HomeActivity.this, RestaurantSearchActivity.class);
                restaurantSearch.putExtra("customerdetails",customerdetails);
                HomeActivity.this.startActivity(restaurantSearch);
                //HomeActivity.this.finish();

            }
        });
        rlFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent foodSearch = new Intent(HomeActivity.this, SearchByFoodTypeActivity.class);
                foodSearch.putExtra("customerdetails",customerdetails);
                HomeActivity.this.startActivity(foodSearch);
                //HomeActivity.this.finish();

            }
        });
        rlLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent locationSearch = new Intent(HomeActivity.this, SearchByLocation.class);
                locationSearch.putExtra("customerdetails",customerdetails);
                HomeActivity.this.startActivity(locationSearch);
                //HomeActivity.this.finish();

            }
        });


        //Menu Bar functions
        bBookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Bookings = new Intent(HomeActivity.this, ViewBookingsActivity.class);
                Bookings.putExtra("customerdetails", customerdetails);
                HomeActivity.this.startActivity(Bookings);
            }
        });
        bProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myprofile = new Intent(HomeActivity.this, MyProfile.class);
                myprofile.putExtra("customerdetails", customerdetails);
                HomeActivity.this.startActivity(myprofile);
            }
        });
        //

    }
}
