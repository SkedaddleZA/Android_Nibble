package com.nibble.skedaddle.nibble.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nibble.skedaddle.nibble.R;

public class BookingConfirmationActivity extends AppCompatActivity {
    private String[] customerdetails;
    private String bookingrequestid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_confirmation);

        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        final Intent Booking = getIntent();
        customerdetails = Booking.getStringArrayExtra("customerdetails");
        bookingrequestid= Booking.getStringExtra("bookingrequestid");
    }
}
