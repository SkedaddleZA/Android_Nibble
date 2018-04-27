package com.nibble.skedaddle.nibble.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nibble.skedaddle.nibble.R;

public class HomeActivity extends AppCompatActivity {//my tollie

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        Intent intent = getIntent();
        final String[] customerdetails = intent.getStringArrayExtra("customerdetails");


        TextView tvDetails = (TextView) findViewById(R.id.tvDetails);

        tvDetails.setText("Signed in as " + customerdetails[1] + " " + customerdetails[2]);

        RelativeLayout rlRestaurant;


        rlRestaurant = findViewById(R.id.rlRestaurant);

        rlRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent restaurantSearch = new Intent(HomeActivity.this, RestaurantSearchActivity.class);
                restaurantSearch.putExtra("customerdetails",customerdetails);
                HomeActivity.this.startActivity(restaurantSearch);

            }
        });

    }
}
