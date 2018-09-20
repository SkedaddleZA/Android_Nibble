package com.nibble.skedaddle.nibble.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.nibble.skedaddle.nibble.CustomListAdapters.FAQList;
import com.nibble.skedaddle.nibble.CustomListAdapters.ReviewList;
import com.nibble.skedaddle.nibble.CustomModels.FAQModel;
import com.nibble.skedaddle.nibble.CustomModels.ReviewModel;
import com.nibble.skedaddle.nibble.R;
import com.nibble.skedaddle.nibble.classes.Customer;
import com.nibble.skedaddle.nibble.classes.Review;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class FAQActivity extends AppCompatActivity {
    private String[] customerdetails;
    private ArrayList<FAQModel> faqmodel;
    private JSONArray result;
    private RequestQueue requestQueue;
    private ListView lvFAQ;
    private RelativeLayout bHome, bBookings, bProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        final Intent faq = getIntent();
        customerdetails = faq.getStringArrayExtra("customerdetails");
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        faqmodel = new ArrayList<>();
        lvFAQ = findViewById(R.id.lvFAQ);
        bHome = findViewById(R.id.bHome);
        bBookings = findViewById(R.id.bBookings);
        bProfile = findViewById(R.id.bProfile);

        bHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Home = new Intent(FAQActivity.this, HomeActivity.class);
                Home.putExtra("customerdetails", customerdetails);
                FAQActivity.this.startActivity(Home);
                FAQActivity.this.finish();
            }
        });
        bBookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Bookings = new Intent(FAQActivity.this, ViewBookingsActivity.class);
                Bookings.putExtra("customerdetails", customerdetails);
                FAQActivity.this.startActivity(Bookings);
                FAQActivity.this.finish();
            }
        });
        bProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myprofile = new Intent(FAQActivity.this, MyProfile.class);
                myprofile.putExtra("customerdetails", customerdetails);
                FAQActivity.this.startActivity(myprofile);
                FAQActivity.this.finish();
            }
        });
        //


        FillListView();
    }


    private void FillListView(){
        faqmodel.clear();
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject j;
                try {
                    j = new JSONObject(response);
                    result = j.getJSONArray("faq");
                    for(int i=0;i<result.length();i++){
                        try {
                            JSONObject json = result.getJSONObject(i);

                            faqmodel.add(new FAQModel(json.getString("question"),json.getString("answer")));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //FIlls lvRestaurants with all items from restlist array
                lvFAQ.setAdapter(new FAQList(faqmodel,getApplicationContext()));


            }
        };
        Customer faq = new Customer();
        faq.GetFAQ(responseListener, requestQueue);


    }

}
