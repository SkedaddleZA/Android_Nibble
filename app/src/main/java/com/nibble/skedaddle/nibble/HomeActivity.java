package com.nibble.skedaddle.nibble;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        Intent intent = getIntent();
        final int customerid = intent.getIntExtra("customerid", 1);
        String firstname = intent.getStringExtra("firstname");
        String lastname = intent.getStringExtra("lastname");
        String email = intent.getStringExtra("email");
        String phone = intent.getStringExtra("phone");
        TextView tvDetails = (TextView) findViewById(R.id.tvDetails);

        tvDetails.setText(customerid + " " + firstname + " " + lastname + " " + email + " " + phone);

        RelativeLayout rlRestaurant;


        rlRestaurant = findViewById(R.id.rlRestaurant);

        rlRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent restaurantSearch = new Intent(HomeActivity.this, RestaurantSearchActivity.class);
                restaurantSearch.putExtra("customerid",customerid);
                HomeActivity.this.startActivity(restaurantSearch);

            }
        });

    }
}
