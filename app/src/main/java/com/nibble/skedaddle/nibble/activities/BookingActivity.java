package com.nibble.skedaddle.nibble.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.nibble.skedaddle.nibble.R;
import com.nibble.skedaddle.nibble.classes.Customer;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;

public class BookingActivity extends AppCompatActivity {

    private CalendarView cvDate;
    private TimePicker tpTime;
    private Button bBook, bNext;
    private TextView tvRestaurant;
    private String time, date,datetime, comment, nowdate, nowdatetime;
    private SimpleDateFormat sdf;
    private RequestQueue requestQueue;
    private EditText etNum, etComment;
    private LinearLayout llstep2, llstep1;
    private ImageView restImage;
    private RelativeLayout bHome, bBookings, bProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        final Intent rbooking = getIntent();
        final String[] restaurantdetails = rbooking.getStringArrayExtra("restaurantdetails");
        final String[] customerdetails = rbooking.getStringArrayExtra("customerdetails");

        etComment = findViewById(R.id.etComment);
        restImage = findViewById(R.id.restImage);
        cvDate = findViewById(R.id.cvDate);
        tpTime = findViewById(R.id.tpTime);
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        bBook = findViewById(R.id.bBook);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        etNum = findViewById(R.id.etNum);
        comment = "";
        llstep2 = findViewById(R.id.llstep2);
        llstep1 = findViewById(R.id.llstep1);
        bNext = findViewById(R.id.bNext);
        llstep2.setVisibility(View.INVISIBLE);
        bHome = findViewById(R.id.bHome);
        bBookings = findViewById(R.id.bBookings);
        bProfile = findViewById(R.id.bRProfile);

        //Decode Base64 string (Image) from the array and display it in ImageView
        restImage = findViewById(R.id.restImage);
        byte[] decodedString = Base64.decode(restaurantdetails[14],Base64.DEFAULT);
        InputStream inputStream  = new ByteArrayInputStream(decodedString);
        Bitmap bitmap  = BitmapFactory.decodeStream(inputStream);
        restImage.setImageBitmap(bitmap);

        //Menu Bar Functions
        bHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Home = new Intent(BookingActivity.this, HomeActivity.class);
                Home.putExtra("customerdetails", customerdetails);
                BookingActivity.this.startActivity(Home);
                BookingActivity.this.finish();
            }
        });
        bBookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Bookings = new Intent(BookingActivity.this, ViewBookingsActivity.class);
                Bookings.putExtra("customerdetails", customerdetails);
                BookingActivity.this.startActivity(Bookings);
            }
        });
        //




        bNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llstep1.setVisibility(View.INVISIBLE);
                llstep2.setVisibility(View.VISIBLE);
            }
        });

        cvDate.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                date = String.valueOf(year) + "-" + String.valueOf(month+1) + "-" + String.valueOf(dayOfMonth);
            }
        });

        bBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (date == null)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(BookingActivity.this);
                    builder.setMessage("Please enter date.")
                            .setNegativeButton("OK", null)
                            .create()
                            .show();
                }else {
                    int hour = tpTime.getHour();
                    int minute = tpTime.getMinute();
                    time = String.valueOf(hour) + ":" + String.valueOf(minute) + ":00";
                    datetime = date + " " + time;
                    nowdatetime = sdf.format(cvDate.getDate());

                    comment = etComment.getText().toString();
                    //tvRestaurant.setText(nowdatetime);
                    //tvRestaurant.setText(datetime);


                   Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {//Method that triggers when response is received from the POST
                            try {
                                JSONObject jsonResponse = new JSONObject(response);//create JSONOBject called jsonResponse which contains all the data that was responded from the POST
                                boolean success = jsonResponse.getBoolean("success");//get the boolean value which is in the "success" holder IN the JSON output and put it in a boolean
                                if (success) {//if the process was successful go to login screen
                                    Intent Home = new Intent(BookingActivity.this, HomeActivity.class);
                                    Home.putExtra("customerdetails", customerdetails);
                                    BookingActivity.this.startActivity(Home);
                                    BookingActivity.this.finish();


                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(BookingActivity.this);
                                    builder.setMessage("Request failed.")
                                            .setNegativeButton("Retry", null)
                                            .create()
                                            .show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    Customer bookingRequest = new Customer();//Create Customer Object
                    bookingRequest.RequestBooking(customerdetails[0], restaurantdetails[0], datetime, etNum.getText().toString(), comment, nowdatetime, date, time, responseListener,requestQueue);//Call the CreateBOoking request procedure


                }


            }
        });




    }
}