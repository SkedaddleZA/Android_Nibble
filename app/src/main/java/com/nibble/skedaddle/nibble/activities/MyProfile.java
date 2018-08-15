package com.nibble.skedaddle.nibble.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.nibble.skedaddle.nibble.R;

public class MyProfile extends AppCompatActivity {


    private LinearLayout llStep1, llStep2;
    private EditText etName, etSurname, etEmail, etPhone, etCP, etNP;
    private Button bChangePasswordStart, bChangePasswordFinish, bSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        Intent myprofile = getIntent();
        final String[] customerdetails = myprofile.getStringArrayExtra("customerdetails");

        llStep1 = findViewById(R.id.llStep1);
        llStep2 = findViewById(R.id.llStep2);

        llStep2.setVisibility(View.VISIBLE);
        llStep2.setVisibility(View.INVISIBLE);


    }
}
