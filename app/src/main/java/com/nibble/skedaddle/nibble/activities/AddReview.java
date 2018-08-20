package com.nibble.skedaddle.nibble.activities;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.nibble.skedaddle.nibble.CommonMethods;
import com.nibble.skedaddle.nibble.R;
import com.nibble.skedaddle.nibble.classes.Customer;
import com.nibble.skedaddle.nibble.classes.Review;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddReview extends AppCompatActivity {

    private String[] restaurantfulldetails, customerdetails;
    private String rating = "1";
    private ImageView iv1, iv2, iv3, iv4, iv5;
    private EditText etContent;
    private Button bSReview;
    private RequestQueue requestQueue;
    private RelativeLayout bHome,bBookings, bProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);

        final Intent addReview = getIntent();
        restaurantfulldetails = addReview.getStringArrayExtra("restaurantdetails");
        customerdetails = addReview.getStringArrayExtra("customerdetails");

        bHome = findViewById(R.id.bHome);
        bBookings = findViewById(R.id.bBookings);
        bProfile = findViewById(R.id.bProfile);

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        iv1 = findViewById(R.id.iv1);
        iv2 = findViewById(R.id.iv2);
        iv3 = findViewById(R.id.iv3);
        iv4 = findViewById(R.id.iv4);
        iv5 = findViewById(R.id.iv5);
        etContent = findViewById(R.id.etContent);
        bSReview = findViewById(R.id.bSReview);

        final Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("star", R.drawable.star);
        map.put("nostar", R.drawable.nostar);



        //Menu Bar Functions
        bHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Home = new Intent(AddReview.this, HomeActivity.class);
                Home.putExtra("customerdetails", customerdetails);
                AddReview.this.startActivity(Home);
                AddReview.this.finish();
            }
        });
        bBookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Bookings = new Intent(AddReview.this, ViewBookingsActivity.class);
                Bookings.putExtra("customerdetails", customerdetails);
                AddReview.this.startActivity(Bookings);
            }
        });
        bProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myprofile = new Intent(AddReview.this, MyProfile.class);
                myprofile.putExtra("customerdetails", customerdetails);
                AddReview.this.startActivity(myprofile);
            }
        });
        //



        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rating = "1";
                iv1.setImageResource(map.get("star"));
                iv2.setImageResource(map.get("nostar"));
                iv3.setImageResource(map.get("nostar"));
                iv4.setImageResource(map.get("nostar"));
                iv5.setImageResource(map.get("nostar"));
            }
        });
        iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rating = "2";
                iv1.setImageResource(map.get("star"));
                iv2.setImageResource(map.get("star"));
                iv3.setImageResource(map.get("nostar"));
                iv4.setImageResource(map.get("nostar"));
                iv5.setImageResource(map.get("nostar"));
            }
        });
        iv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rating = "3";
                iv1.setImageResource(map.get("star"));
                iv2.setImageResource(map.get("star"));
                iv3.setImageResource(map.get("star"));
                iv4.setImageResource(map.get("nostar"));
                iv5.setImageResource(map.get("nostar"));
            }
        });
        iv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rating = "4";
                iv1.setImageResource(map.get("star"));
                iv2.setImageResource(map.get("star"));
                iv3.setImageResource(map.get("star"));
                iv4.setImageResource(map.get("star"));
                iv5.setImageResource(map.get("nostar"));
            }
        });
        iv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rating = "5";
                iv1.setImageResource(map.get("star"));
                iv2.setImageResource(map.get("star"));
                iv3.setImageResource(map.get("star"));
                iv4.setImageResource(map.get("star"));
                iv5.setImageResource(map.get("star"));
            }
        });

        bSReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etContent.getText().toString().matches("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddReview.this);
                    builder.setMessage("Please type a review.")
                            .setNegativeButton("OK", null)
                            .create()
                            .show();
                }else{

                    String comment = etContent.getText().toString();

                    Date c = Calendar.getInstance().getTime();

                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String formattedDate = df.format(c);


                    //Create a response listener which listens if there is a response, and stores the response
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {//Method that triggers when response is received from the POST
                            try {
                                JSONObject jsonResponse = new JSONObject(response);//create JSONOBject called jsonResponse which contains all the data that was responded from the POST
                                boolean success = jsonResponse.getBoolean("success");//get the boolean value which is in the "success" holder IN the JSON output and put it in a boolean

                                if (success) {//if the process was successful go to login screen
                                    Intent viewreviews = new Intent(AddReview.this, ViewReviews.class);
                                    viewreviews.putExtra("customerdetails", customerdetails);
                                    viewreviews.putExtra("restaurantdetails", restaurantfulldetails);
                                    AddReview.this.startActivity(viewreviews);


                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(AddReview.this);
                                    builder.setMessage("Failed to submit review.")
                                            .setNegativeButton("Retry", null)
                                            .create()
                                            .show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    Review addreview = new Review();//Create review Object
                    addreview.InsertReview(customerdetails[0], restaurantfulldetails[0], comment, formattedDate, rating, responseListener, requestQueue);//Call the insertreview procedure and send all required data to review Class

                }

            }
        });



    }
}
