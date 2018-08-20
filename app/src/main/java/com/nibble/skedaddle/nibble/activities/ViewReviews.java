package com.nibble.skedaddle.nibble.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.nibble.skedaddle.nibble.CustomListAdapters.RestList;
import com.nibble.skedaddle.nibble.CustomListAdapters.ReviewList;
import com.nibble.skedaddle.nibble.CustomModels.RestaurantModel;
import com.nibble.skedaddle.nibble.CustomModels.ReviewModel;
import com.nibble.skedaddle.nibble.R;
import com.nibble.skedaddle.nibble.classes.Restaurant;
import com.nibble.skedaddle.nibble.classes.Review;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ViewReviews extends AppCompatActivity {

    private Button bAddReview;

    private String[] restaurantfulldetails, customerdetails;
    private ArrayList<ReviewModel> revmodel;
    private JSONArray result;
    private RequestQueue requestQueue;
    private ListView lvReview;
    private RelativeLayout bHome,bBookings, bProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reviews);

        final Intent reviews = getIntent();
        restaurantfulldetails = reviews.getStringArrayExtra("restaurantdetails");
        customerdetails = reviews.getStringArrayExtra("customerdetails");
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        bHome = findViewById(R.id.bHome);
        bBookings = findViewById(R.id.bBookings);
        bProfile = findViewById(R.id.bProfile);

        revmodel = new ArrayList<>();
        bAddReview=findViewById(R.id.bAddReview);
        lvReview = findViewById(R.id.lvReview);
        FillListView();


        //Menu Bar Functions
        bHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Home = new Intent(ViewReviews.this, HomeActivity.class);
                Home.putExtra("customerdetails", customerdetails);
                ViewReviews.this.startActivity(Home);
                ViewReviews.this.finish();
            }
        });
        bBookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Bookings = new Intent(ViewReviews.this, ViewBookingsActivity.class);
                Bookings.putExtra("customerdetails", customerdetails);
                ViewReviews.this.startActivity(Bookings);
            }
        });
        bProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myprofile = new Intent(ViewReviews.this, MyProfile.class);
                myprofile.putExtra("customerdetails", customerdetails);
                ViewReviews.this.startActivity(myprofile);
            }
        });
        //



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


    private void FillListView(){
        revmodel.clear();
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject j;
                try {
                    j = new JSONObject(response);
                    result = j.getJSONArray("reviews");
                    for(int i=0;i<result.length();i++){
                        try {
                            JSONObject json = result.getJSONObject(i);
                            String date = json.getString("date");
                            String OGDate="yyy-MM-dd";
                            String EndDate="dd-MMM";
                            String DateFormat="";
                            SimpleDateFormat DateFormated = new SimpleDateFormat(OGDate);
                            Date myDate = null;
                            try {
                                myDate = DateFormated.parse(date);
                            } catch (java.text.ParseException e) {
                                e.printStackTrace();
                            }
                            SimpleDateFormat timeFormat = new SimpleDateFormat(EndDate);
                            DateFormat = timeFormat.format(myDate);

                            revmodel.add(new ReviewModel(json.getString("comment"), DateFormat,json.getString("name"),Integer.toString(json.getInt("rating"))));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //FIlls lvRestaurants with all items from restlist array
                lvReview.setAdapter(new ReviewList(revmodel,getApplicationContext()));


            }
        };
        Review reviews = new Review();
        reviews.GetReviews(restaurantfulldetails[0],responseListener, requestQueue);


    }


}
