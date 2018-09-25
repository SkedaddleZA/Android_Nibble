package com.nibble.skedaddle.nibble.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.nibble.skedaddle.nibble.CommonMethods;
import com.nibble.skedaddle.nibble.R;
import com.nibble.skedaddle.nibble.classes.Customer;
import com.nibble.skedaddle.nibble.classes.SaveSharedPreference;

import org.json.JSONException;
import org.json.JSONObject;

public class MyProfile extends AppCompatActivity {


    private LinearLayout llStep1, llStep2;
    private EditText etName, etSurname, etEmail, etPhone, etCP, etNP;
    private Button bChangePasswordStart, bChangePasswordFinish, bSave, bBack, bLogOut;
    private RelativeLayout bHome, bBookings;
    private String customerid, name, surname, email, phone, password, oldpassword, currentpassword;
    private RequestQueue requestQueue;
    private boolean passwordChange;
    TextView tvLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        Intent myprofile = getIntent();
        final String[] customerdetails = myprofile.getStringArrayExtra("customerdetails");

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        llStep1 = findViewById(R.id.llStep1);
        llStep2 = findViewById(R.id.llStep2);
        bChangePasswordStart = findViewById(R.id.bChangePasswordStart);
        bChangePasswordFinish = findViewById(R.id.bChangePasswordFinish);
        etName = findViewById(R.id.etName);
        etSurname = findViewById(R.id.etSurname);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etCP = findViewById(R.id.etCP);
        etNP = findViewById(R.id.etNP);
        bSave = findViewById(R.id.bSave);
        tvLogout = findViewById(R.id.tvLogout);
        bBack = findViewById(R.id.bBack);
        bHome = findViewById(R.id.bHome);
        bBookings = findViewById(R.id.bBookings);
        bChangePasswordFinish.setEnabled(false);

        customerid = customerdetails[0];
        name = customerdetails[1];
        surname = customerdetails[2];
        email = customerdetails[3];
        phone = customerdetails[4];
        oldpassword = customerdetails[5];
        password = customerdetails[5];
        passwordChange = false;


        etName.setText(name);
        etSurname.setText(surname);
        etEmail.setText(email);
        etPhone.setText(phone);



        llStep1.setVisibility(View.VISIBLE);
        llStep2.setVisibility(View.INVISIBLE);



        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveSharedPreference.clearUserName(getApplicationContext());
                Intent login = new Intent(MyProfile.this, LoginActivity.class);
                MyProfile.this.startActivity(login);
                MyProfile.this.finish();
            }
        });

        bHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Home = new Intent(MyProfile.this, HomeActivity.class);
                Home.putExtra("customerdetails", customerdetails);
                MyProfile.this.startActivity(Home);
                MyProfile.this.finish();
            }
        });
        bBookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Bookings = new Intent(MyProfile.this, ViewBookingsActivity.class);
                Bookings.putExtra("customerdetails", customerdetails);
                MyProfile.this.startActivity(Bookings);
                MyProfile.this.finish();
            }
        });
        //

        bBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llStep1.setVisibility(View.VISIBLE);
                llStep2.setVisibility(View.INVISIBLE);
            }
        });

        bChangePasswordStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llStep2.setVisibility(View.VISIBLE);
                llStep1.setVisibility(View.INVISIBLE);
            }
        });

        etNP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(etNP.getText().toString().matches("") || etNP.getText().toString().length() < 8 || etCP.getText().toString().matches("") || etCP.getText().toString().length() < 8){
                    bChangePasswordFinish.setEnabled(false);
                }else{
                    bChangePasswordFinish.setEnabled(true);
                }
            }
        });

        etCP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(etCP.getText().toString().matches("") || etCP.getText().toString().length() < 8 || etNP.getText().toString().matches("") || etNP.getText().toString().length() < 8){
                    bChangePasswordFinish.setEnabled(false);
                }else{
                    bChangePasswordFinish.setEnabled(true);
                }
            }
        });

        bChangePasswordFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                currentpassword = CommonMethods.sha256(etCP.getText().toString());
                if(currentpassword.matches(oldpassword)) {
                    password = etNP.getText().toString();
                    llStep1.setVisibility(View.VISIBLE);
                    llStep2.setVisibility(View.INVISIBLE);
                }
                else {
                    AlertDialog.Builder builder3 = new AlertDialog.Builder(MyProfile.this);
                    builder3.setMessage("You entered wrong current password.")
                            .setNegativeButton("Retry", null)
                            .create()
                            .show();
                    bChangePasswordFinish.setEnabled(false);

                }
            }
        });

        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etName.getText().toString().matches("")) {
                    messageshow("Please enter name.");
                }else if(etSurname.getText().toString().matches(""))  {
                    messageshow("Please enter surname.");
                }else if(etEmail.getText().toString().matches(""))  {
                    messageshow("Please enter email.");
                }  else if(etPhone.getText().toString().matches("")) {
                    messageshow("Please enter phone.");
                }else if(!CommonMethods.CheckEmail(etEmail.getText().toString())){
                    messageshow("Please enter correct email");
                }else if(!CommonMethods.CheckPhone(etPhone.getText().toString())) {
                    messageshow("Please enter correct phone number.");
                }else {

                    name = etName.getText().toString();
                    surname = etSurname.getText().toString();
                    email = etEmail.getText().toString();
                    phone = etPhone.getText().toString();


                    //Create a response listener which listens if there is a response, and stores the response
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {//Method that triggers when response is received from the POST
                            try {
                                JSONObject jsonResponse = new JSONObject(response);//create JSONOBject called jsonResponse which contains all the data that was responded from the POST
                                boolean success = jsonResponse.getBoolean("success");//get the boolean value which is in the "success" holder IN the JSON output and put it in a boolean
                                if (success) {//if the process was successful go to login screen
                                    AlertDialog.Builder builder2 = new AlertDialog.Builder(MyProfile.this);
                                    builder2.setMessage("Details has been changed.")
                                            .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Intent Home = new Intent(MyProfile.this, HomeActivity.class);
                                                    customerdetails[1] = name;
                                                    customerdetails[2] = surname;
                                                    customerdetails[3] = email;
                                                    customerdetails[4] = phone;
                                                    SaveSharedPreference.clearUserName(getApplicationContext());
                                                    if(passwordChange)
                                                        customerdetails[5] =  CommonMethods.sha256(password);


                                                    Home.putExtra("customerdetails",customerdetails);
                                                    MyProfile.this.startActivity(Home);
                                                    MyProfile.this.finish();
                                                }
                                                })
                                            .create()
                                            .show();



                                } else {
                                    AlertDialog.Builder builder2 = new AlertDialog.Builder(MyProfile.this);
                                    builder2.setMessage("Failed to change your details.")
                                            .setNegativeButton("Retry", null)
                                            .create()
                                            .show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    Customer updateCustomer = new Customer();//Create Customer Object

                    if (passwordChange)
                        updateCustomer.UpdateCustomer(customerid, name, surname, email, phone, CommonMethods.sha256(password), responseListener, requestQueue);//Call the UpdateCustomer procedure and send all required data to Customer Class
                    else
                        updateCustomer.UpdateCustomer(customerid, name, surname, email, phone, password, responseListener, requestQueue);//Call the UpdateCustomer procedure and send all required data to Customer Class
                }
            }
        });


    }

    public void messageshow(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(MyProfile.this);
        builder.setMessage(message)
                .setNegativeButton("Retry", null)
                .create()
                .show();
    }


}
