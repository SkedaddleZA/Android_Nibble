package com.nibble.skedaddle.nibble.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.nibble.skedaddle.nibble.CommonMethods;
import com.nibble.skedaddle.nibble.R;
import com.nibble.skedaddle.nibble.classes.BookingRequest;
import com.nibble.skedaddle.nibble.classes.Customer;
import com.nibble.skedaddle.nibble.classes.Restaurant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class BookingConfirmationActivity extends AppCompatActivity {
    private String[] customerdetails;
    private String bookingrequestid;
    private Button bConfirm, bCancel,bRProfile;
    private RelativeLayout bHome,bBookings,bProfile;
    private RequestQueue requestQueue;
    private TextView rText,dText,tText,gText;
    private JSONArray result;
    private ImageView restImage;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_confirmation);
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        final Intent Booking = getIntent();
        customerdetails = Booking.getStringArrayExtra("customerdetails");
        bookingrequestid= Booking.getStringExtra("bookingrequestid");

        bHome=findViewById(R.id.bHome);
        bBookings=findViewById(R.id.bBookings);
        bProfile=findViewById(R.id.bProfile);
        bConfirm=findViewById(R.id.bConfirm);
        bCancel=findViewById(R.id.bCancel);
        bRProfile=findViewById(R.id.bRProfile);
        rText=findViewById(R.id.rText);
        dText=findViewById(R.id.dText);
        tText=findViewById(R.id.tText);
        gText=findViewById(R.id.gText);
        restImage=findViewById(R.id.restuarant_image);

        fillTextViews();



        //Menu Bar functions
        bHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Home = new Intent(BookingConfirmationActivity.this, HomeActivity.class);
                Home.putExtra("customerdetails", customerdetails);
                BookingConfirmationActivity.this.startActivity(Home);
                BookingConfirmationActivity.this.finish();
            }
        });
        bBookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Bookings = new Intent(BookingConfirmationActivity.this, ViewBookingsActivity.class);
                Bookings.putExtra("customerdetails", customerdetails);
                BookingConfirmationActivity.this.startActivity(Bookings);
            }
        });
        //

        bConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {//Method that triggers when response is received from the POST
                        try {
                            JSONObject jsonResponse = new JSONObject(response);//create JSONOBject called jsonResponse which contains all the data that was responded from the POST
                            boolean success = jsonResponse.getBoolean("success");//get the boolean value which is in the "success" holder IN the JSON output and put it in a boolean
                            if (success) {//if the process was successful go to login screen
                                Intent viewBookingIntent = new Intent(BookingConfirmationActivity.this, ViewBookingsActivity.class);
                                viewBookingIntent.putExtra("customerdetails", customerdetails);
                                BookingConfirmationActivity.this.startActivity(viewBookingIntent);


                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(BookingConfirmationActivity.this);
                                builder.setMessage("Register Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                BookingRequest updateBookingRequest = new BookingRequest();//Create Customer Object
                updateBookingRequest.UpdateBookingStatus(bookingrequestid,"Y", responseListener,requestQueue);//Call the InsertCustomer procedure and send all required data to Customer Class


            }
        });

        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {//Method that triggers when response is received from the POST
                        try {
                            JSONObject jsonResponse = new JSONObject(response);//create JSONOBject called jsonResponse which contains all the data that was responded from the POST
                            boolean success = jsonResponse.getBoolean("success");//get the boolean value which is in the "success" holder IN the JSON output and put it in a boolean
                            if (success) {//if the process was successful go to login screen
                                Intent viewBookingIntent = new Intent(BookingConfirmationActivity.this, ViewBookingsActivity.class);
                                viewBookingIntent.putExtra("customerdetails", customerdetails);
                                BookingConfirmationActivity.this.startActivity(viewBookingIntent);


                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(BookingConfirmationActivity.this);
                                builder.setMessage("Register Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                BookingRequest updateBookingRequest = new BookingRequest();//Create Customer Object
                updateBookingRequest.UpdateBookingStatus(bookingrequestid,"N", responseListener,requestQueue);//Call the InsertCustomer procedure and send all required data to Customer Class


            }
        });




    }
    private void fillTextViews()
    {
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
                            rText.setText("\t\tRestaurant name: "+json.getString("restaurantname"));
                            dText.setText("\t\tDate: "+json.getString("date"));
                            tText.setText("\t\tTime: "+json.getString("time").substring(0,5));
                            gText.setText("\t\tGuests: "+(Integer.toString(json.getInt("numofguests"))));

                            byte[] decodedString = Base64.decode(json.getString("logo"),Base64.DEFAULT);
                            InputStream inputStream  = new ByteArrayInputStream(decodedString);
                            Bitmap bitmap  = BitmapFactory.decodeStream(inputStream);
                            restImage.setImageBitmap(bitmap);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.GetBookingDetails(bookingrequestid,responseListener, requestQueue);

    }
}
