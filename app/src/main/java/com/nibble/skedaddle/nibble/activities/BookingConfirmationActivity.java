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
import android.widget.ProgressBar;
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
import java.text.SimpleDateFormat;
import java.util.Date;

public class BookingConfirmationActivity extends AppCompatActivity {
    private String[] customerdetails, bookingrequestdetails, restaurantfulldetails;
    private String bookingrequestid;
    private Button bConfirm, bCancel,bRProfile;
    private RelativeLayout bHome,bBookings,bProfile;
    private RequestQueue requestQueue;
    private TextView cText,dText,tText,gText,rsText;
    private JSONArray result;
    private ImageView restImage;
    private ProgressBar pbLoadRest;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_confirmation);
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        final Intent br = getIntent();
        bookingrequestdetails= br.getStringArrayExtra("bookingrequestdetails");
        customerdetails = br.getStringArrayExtra("customerdetails");
        bookingrequestid = br.getStringExtra("bookingrequestid");

        restaurantfulldetails = new String[15];
        bHome=findViewById(R.id.bHome);
        bBookings=findViewById(R.id.bBookings);
        bProfile=findViewById(R.id.bProfile);
        bConfirm=findViewById(R.id.bConfirm);
        bCancel=findViewById(R.id.bCancel);
        bRProfile=findViewById(R.id.bRProfile);
        cText=findViewById(R.id.cText);
        dText=findViewById(R.id.dText);
        tText=findViewById(R.id.tText);
        gText=findViewById(R.id.gText);
        rsText=findViewById(R.id.rsText);
        restImage=findViewById(R.id.restuarant_image);
        pbLoadRest=findViewById(R.id.pb_loadrest);
        pbLoadRest.setVisibility(View.INVISIBLE);

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
        bProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myprofile = new Intent(BookingConfirmationActivity.this, MyProfile.class);
                myprofile.putExtra("customerdetails", customerdetails);
                BookingConfirmationActivity.this.startActivity(myprofile);
            }
        });
        //

        if(bookingrequestdetails[6].matches("Y"))
            bConfirm.setEnabled(false);
        else if(bookingrequestdetails[6].matches("N"))
            bCancel.setEnabled(false);


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

        bRProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pbLoadRest.setVisibility(View.VISIBLE);
                GotoRestaurant();

            }
        });

    }
    private void fillTextViews() {
        String Confirm="";
        if (bookingrequestdetails[6].matches("Y")) {
            Confirm="Confirmed";
        }
        else if (bookingrequestdetails[6].matches("N")){
            Confirm="Canceled";
        }
        else if (bookingrequestdetails[6].matches("P")){
            Confirm="Pending";
        }
        String RestStatus="";
        if (bookingrequestdetails[7].matches("Y")) {
            RestStatus="Confirmed";
        }
        else if (bookingrequestdetails[7].matches("N")){
            RestStatus="Canceled";
        }
        else if (bookingrequestdetails[7].matches("P")){
            RestStatus="Pending";
        }

        String date = bookingrequestdetails[2];
        String OGDate="yyy-MM-dd";
        String EndDate="dd MMMM";
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

        dText.setText("\t\tDate: "+DateFormat);
        tText.setText("\t\tTime: "+bookingrequestdetails[3].substring(0,5));
        gText.setText("\t\tGuests: "+bookingrequestdetails[4]);
        cText.setText("\t\tMy Status: "+Confirm);
        rsText.setText("\t\tRestaurant Response: "+RestStatus);
        byte[] decodedString = Base64.decode(bookingrequestdetails[5], Base64.DEFAULT);
        InputStream inputStream = new ByteArrayInputStream(decodedString);
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        restImage.setImageBitmap(bitmap);
    }

    private void GotoRestaurant() {
        //Start fetch restaurant details on click
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject j;
                try {
                    j = new JSONObject(response);
                    result = j.getJSONArray("restaurant");
                    for(int i=0;i<result.length();i++){
                        try {
                            JSONObject json = result.getJSONObject(i);
                            restaurantfulldetails[0] = Integer.toString(json.getInt("restaurantid"));
                            restaurantfulldetails[1] = json.getString("restaurantname");
                            restaurantfulldetails[2] = json.getString("phone");
                            restaurantfulldetails[3] = json.getString("restauranttype");
                            restaurantfulldetails[4] = json.getString("gpslocation");
                            restaurantfulldetails[5] = json.getString("email");
                            restaurantfulldetails[6] = json.getString("addressline1");
                            restaurantfulldetails[7] = json.getString("addressline2");
                            restaurantfulldetails[8] = json.getString("suburbname");
                            restaurantfulldetails[9] = json.getString("postalcode");
                            restaurantfulldetails[10] = json.getString("cityname");
                            restaurantfulldetails[11] = json.getString("opentime");
                            restaurantfulldetails[12] = json.getString("closetime");
                            restaurantfulldetails[13] = json.getString("websiteurl");
                            restaurantfulldetails[14] = json.getString("logo");

                            pbLoadRest.setVisibility(View.INVISIBLE); //Due to non instant loading
                            Intent restaurantintent2 = new Intent(BookingConfirmationActivity.this, RestaurantActivity.class);
                            restaurantintent2.putExtra("restaurantdetails",restaurantfulldetails);
                            restaurantintent2.putExtra("customerdetails",customerdetails);
                            BookingConfirmationActivity.this.startActivity(restaurantintent2);
                            //RestaurantSearchActivity.this.finish();//THIS IS A WORKAROUND TO NOT KNOWING HOW TO REFRESH A PREVIOUS SCREEN WHEN ANDROID BACK BUTTON PRESSED
                            //FIXED USING DIFFEREN JSON OBJECT
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        Restaurant restaurantobject2 = new Restaurant();
        restaurantobject2.GetRestaurantDetails(bookingrequestdetails[0], responseListener, requestQueue);
        //end of fetch






    }

}
