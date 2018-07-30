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
import android.widget.ProgressBar;
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

public class ViewBookingsActivity extends AppCompatActivity {
    private ListView lvMyBooking;
    private String[] restaurantdetails, customerdetails, bookingrequestdetails;
    private JSONArray result, restresult;
    private RequestQueue requestQueue;
    private RelativeLayout bHome,bProfile;
    private ArrayList<String> restlist;
    private SimpleDateFormat sdf;
    private ProgressBar pbLoadRest;
    private String bookingrequestid;
    private TextView tvHeadings;

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
        pbLoadRest = findViewById(R.id.pb_loadrest);
        pbLoadRest.setVisibility(View.INVISIBLE);
        tvHeadings = findViewById(R.id.tvHeadings);

        lvMyBooking = findViewById(R.id.lvMyBookings);
        restaurantdetails = new String[3];
        bookingrequestdetails = new String[7];

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

                    bookingrequestid = Integer.toString(json.getInt("bookingrequestid"));

                    pbLoadRest.setVisibility(View.VISIBLE);

                    GotoBookingRequest();

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
                            restlist.add("Restaurant:"+json.getString("restaurantname") + "\nDate: " + json.getString("date")+"\nGuests: " + json.getString("numofguests"));
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

    private void GotoBookingRequest() {
        //Start fetch restaurant details on click
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject j;
                try {
                    j = new JSONObject(response);
                    restresult = j.getJSONArray("bookingrequests");
                    for(int i=0;i<restresult.length();i++){
                        try {
                            JSONObject json = restresult.getJSONObject(i);
                            bookingrequestdetails[0] = Integer.toString(json.getInt("restaurantid")); // need this to get rest profile button working
                            bookingrequestdetails[1] = json.getString("restaurantname");
                            bookingrequestdetails[2] = json.getString("date");
                            bookingrequestdetails[3] = json.getString("time");
                            bookingrequestdetails[4] = json.getString("numofguests");
                            bookingrequestdetails[5] = json.getString("logo");
                            bookingrequestdetails[6] = json.getString("status");

                            pbLoadRest.setVisibility(View.INVISIBLE); //Due to non instant loading
                            Intent br = new Intent(ViewBookingsActivity.this, BookingConfirmationActivity.class);
                            br.putExtra("bookingrequestdetails",bookingrequestdetails);
                            br.putExtra("customerdetails",customerdetails);
                            br.putExtra("bookingrequestid", bookingrequestid);
                            ViewBookingsActivity.this.startActivity(br);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        BookingRequest getbr = new BookingRequest();
        getbr.GetBookingDetails(bookingrequestid, responseListener, requestQueue);
        //end of fetch






    }

}
