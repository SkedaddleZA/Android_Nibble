package com.nibble.skedaddle.nibble.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.TooltipCompat;
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
import com.nibble.skedaddle.nibble.classes.BookingRequest;
import com.nibble.skedaddle.nibble.classes.Customer;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BookingActivity extends AppCompatActivity {

    private CalendarView cvDate;
    private TimePicker tpTime;
    private Button bBook, bNext, bAdd, bSub;
    private TextView tvDateTime;
    private String time, date,datetime, comment, nowdate, nowdatetime;
    private SimpleDateFormat sdf, sdftime, sdfdate;
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
        sdfdate = new SimpleDateFormat("yyyy-MM-dd");
        sdftime = new SimpleDateFormat("HH:mm:ss");
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
        bAdd = findViewById(R.id.bAdd);
        bSub = findViewById(R.id.bSub);
        tvDateTime = findViewById(R.id.tvdatetime);

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
        bProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myprofile = new Intent(BookingActivity.this, MyProfile.class);
                myprofile.putExtra("customerdetails", customerdetails);
                BookingActivity.this.startActivity(myprofile);
            }
        });
        //

        bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etNum.setText(Integer.toString(Integer.parseInt(etNum.getText().toString()) + 1));
            }
        });
        bSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!etNum.getText().toString().matches("1"))
                etNum.setText(Integer.toString(Integer.parseInt(etNum.getText().toString()) - 1));

            }
        });

        bNext.setEnabled(false);
        bNext.setBackgroundTintList(getResources().getColorStateList(R.color.secondaryLightColor));

        bNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour = tpTime.getHour();
                int minute = tpTime.getMinute();
                time = String.valueOf(hour) + ":" + String.valueOf(minute) + ":00";
                datetime = date + " " + time.substring(0,5);

                try {
                    Date opentime = sdftime.parse(restaurantdetails[11]);
                    Date closetime = sdftime.parse(restaurantdetails[12]);
                    Date myDate = sdfdate.parse(date);
                    nowdatetime = sdf.format(cvDate.getDate());
                    Date nowDate = sdfdate.parse(nowdatetime);
                    Date myTime = sdftime.parse(time);
                    if (myTime.before(opentime) || closetime.before(myTime) ) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(BookingActivity.this);
                        builder.setMessage("Please enter a time when restaurant is open. (" + restaurantdetails[11] + " - " + restaurantdetails[12] + ")" )
                                .setNegativeButton("OK", null)
                                .create()
                                .show();
                    }else{
                        if (date == null || myDate.before(nowDate) )
                        {
                            AlertDialog.Builder builder = new AlertDialog.Builder(BookingActivity.this);
                            builder.setMessage("Please choose valid date.")
                                    .setNegativeButton("OK", null)
                                    .create()
                                    .show();
                        }else {
                            llstep1.setVisibility(View.INVISIBLE);
                            llstep2.setVisibility(View.VISIBLE);
                            tvDateTime.setText(datetime);
                        }
                    }
                    } catch (ParseException e) {
                    e.printStackTrace();
                }




            }
        });

        cvDate.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                bNext.setEnabled(true);
                bNext.setBackgroundTintList(getResources().getColorStateList(R.color.primaryDarkColor));
                date = String.valueOf(year) + "-" + String.valueOf(month+1) + "-" + String.valueOf(dayOfMonth);
            }
        });

        bBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    //nowdatetime = sdf.format(cvDate.getDate());

                    comment = etComment.getText().toString();


                   Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {//Method that triggers when response is received from the POST
                            try {
                                JSONObject jsonResponse = new JSONObject(response);//create JSONOBject called jsonResponse which contains all the data that was responded from the POST
                                boolean success = jsonResponse.getBoolean("success");//get the boolean value which is in the "success" holder IN the JSON output and put it in a boolean
                                if (success) {//if the process was successful go to login screen


                                    AlertDialog.Builder builder = new AlertDialog.Builder(BookingActivity.this);
                                    builder.setMessage("Booking Request successfully made!")
                                            .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Intent Home = new Intent(BookingActivity.this, HomeActivity.class);
                                                    Home.putExtra("customerdetails", customerdetails);
                                                    BookingActivity.this.startActivity(Home);
                                                    BookingActivity.this.finish();
                                                }
                                            })
                                            .create()
                                            .show();



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

                    BookingRequest bookingRequest = new BookingRequest();//Create Customer Object
                    bookingRequest.RequestBooking(customerdetails[0], restaurantdetails[0], etNum.getText().toString(), comment, nowdatetime, date, time, responseListener,requestQueue);//Call the CreateBOoking request procedure


                }



        });




    }
}
