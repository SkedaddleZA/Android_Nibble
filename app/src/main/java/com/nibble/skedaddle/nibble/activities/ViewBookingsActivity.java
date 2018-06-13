package com.nibble.skedaddle.nibble.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.nibble.skedaddle.nibble.R;
import com.nibble.skedaddle.nibble.classes.BookingRequest;
import com.nibble.skedaddle.nibble.classes.Restaurant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class ViewBookingsActivity extends AppCompatActivity {
    private ListView lvMyBooking;
    private String[] restaurantdetails, customerdetails;
    private JSONArray result;
    private RequestQueue requestQueue;
    private RelativeLayout bHome,bProfile;
    private ArrayList<String> restlist;
    private SimpleDateFormat sdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bookings);

        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        final Intent viewbooking = getIntent();
        customerdetails = viewbooking.getStringArrayExtra("customerdetails");
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        restlist = new ArrayList<>();

        bHome = findViewById(R.id.bHome);
        sdf = new SimpleDateFormat("DDD-dd-MMM");

        lvMyBooking = findViewById(R.id.lvMyBookings);
        restaurantdetails = new String[3];

        bHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Home = new Intent(ViewBookingsActivity.this, HomeActivity.class);
                Home.putExtra("customerdetails", customerdetails);
                ViewBookingsActivity.this.startActivity(Home);
                ViewBookingsActivity.this.finish();


            }
        });

        FillListView();

        lvMyBooking.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    JSONObject json = result.getJSONObject(position);
                            Intent Booking = new Intent(ViewBookingsActivity.this, BookingConfirmationActivity.class);
                            Booking.putExtra("customerdetails", customerdetails);
                            Booking.putExtra("bookingrequestid", json.getString("bookingrequestid"));
                            ViewBookingsActivity.this.startActivity(Booking);



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }
    private void FillListView(){
        restlist.clear();
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject j;
                try {
                    j = new JSONObject(response);
                    result = j.getJSONArray("bookingrequests");

                    for(int i=0;i<result.length();i++){
                        try {
                            JSONObject json = result.getJSONObject(i);
                            restlist.add("\tRestaurant: "+(json.getString("restaurantname") + "\n\tDate: " + json.getString("date")+ "\n\tGuests: " + json.getString("numofguests")));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //FIlls lvRestaurants with all items from restlist array
                lvMyBooking.setAdapter(new ArrayAdapter<String>(ViewBookingsActivity.this, android.R.layout.simple_list_item_1, restlist) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        // Get the Item from ListView
                        View view = super.getView(position, convertView, parent);

                        // Initialize a TextView for ListView each Item
                        TextView tv = (TextView) view.findViewById(android.R.id.text1);

                        // Set the text color of TextView (ListView Item)
                        tv.setTextColor(Color.BLACK);

                        // Generate ListView Item using TextView
                        return view;
                    }
                });


            }
        };
        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.GetBookingRequests(customerdetails[0],responseListener, requestQueue);


    }
}
