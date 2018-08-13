package com.nibble.skedaddle.nibble.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

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

import java.util.ArrayList;

public class ViewReviews extends AppCompatActivity {

    private Button bAddReview;

    private String[] restaurantfulldetails, customerdetails;
    private ArrayList<ReviewModel> revmodel;
    private JSONArray result;
    private RequestQueue requestQueue;
    private ListView lvReview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reviews);

        final Intent reviews = getIntent();
        restaurantfulldetails = reviews.getStringArrayExtra("restaurantdetails");
        customerdetails = reviews.getStringArrayExtra("customerdetails");
        requestQueue = Volley.newRequestQueue(getApplicationContext());


        revmodel = new ArrayList<>();
        bAddReview=findViewById(R.id.bAddReview);
        lvReview = findViewById(R.id.lvReview);
        FillListView();

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
                            revmodel.add(new ReviewModel(json.getString("comment"), json.getString("date"),json.getString("name"),Integer.toString(json.getInt("rating"))));

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
