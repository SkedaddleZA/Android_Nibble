package com.nibble.skedaddle.nibble.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.nibble.skedaddle.nibble.R;
import com.nibble.skedaddle.nibble.classes.BookingRequest;
import com.nibble.skedaddle.nibble.classes.Restaurant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ViewBookingsActivity extends AppCompatActivity {
    private ListView lvMyBooking;
    private String[] restaurantdetails, customerdetails, restaurantfulldetails;
    private JSONArray result;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bookings);

        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        final Intent viewbooking = getIntent();
        customerdetails = viewbooking.getStringArrayExtra("customerdetails");
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        ImageView bHome = findViewById(R.id.bHome);
        lvMyBooking = findViewById(R.id.lvMyBooking);
        restaurantdetails = new String[3];
        restaurantfulldetails = new String[3];

        bHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Home = new Intent(ViewBookingsActivity.this, HomeActivity.class);
                Home.putExtra("customerdetails", customerdetails);
                ViewBookingsActivity.this.startActivity(Home);
                ViewBookingsActivity.this.finish();


            }
        });


        lvMyBooking.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    JSONObject json = result.getJSONObject(position);
                    //put all selected restaurant details into an array
                    restaurantdetails[0] = Integer.toString(json.getInt("customerid"));
                    //restaurantdetails[1] = json.getString("restaurantname");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
