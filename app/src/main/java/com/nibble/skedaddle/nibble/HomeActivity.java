package com.nibble.skedaddle.nibble;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();
        String firstname = intent.getStringExtra("firstname");
        String lastname = intent.getStringExtra("lastname");
        String email = intent.getStringExtra("email");
        String phone = intent.getStringExtra("phone");
        TextView tvDetails = (TextView) findViewById(R.id.tvDetails);

        tvDetails.setText(firstname + " " + lastname + " " + email + " " + phone);
    }
}
