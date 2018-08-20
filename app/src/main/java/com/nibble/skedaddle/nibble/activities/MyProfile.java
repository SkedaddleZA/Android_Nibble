package com.nibble.skedaddle.nibble.activities;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.nibble.skedaddle.nibble.CommonMethods;
import com.nibble.skedaddle.nibble.R;
import com.nibble.skedaddle.nibble.classes.Customer;

import org.json.JSONException;
import org.json.JSONObject;

public class MyProfile extends AppCompatActivity {


    private LinearLayout llStep1, llStep2;
    private EditText etName, etSurname, etEmail, etPhone, etCP, etNP;
    private Button bChangePasswordStart, bChangePasswordFinish, bSave;
    private RelativeLayout bHome, bBookings;
    private String customerid, name, surname, email, phone, password, oldpassword, currentpassword;
    private RequestQueue requestQueue;

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
        bHome = findViewById(R.id.bHome);
        bBookings = findViewById(R.id.bBookings);

        customerid = customerdetails[0];
        name = customerdetails[1];
        surname = customerdetails[2];
        email = customerdetails[3];
        phone = customerdetails[4];
        oldpassword = customerdetails[5];
        password = customerdetails[5];


        etName.setText(name);
        etSurname.setText(surname);
        etEmail.setText(email);
        etPhone.setText(phone);



        llStep1.setVisibility(View.VISIBLE);
        llStep2.setVisibility(View.INVISIBLE);


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
            }
        });
        //

        bChangePasswordStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llStep2.setVisibility(View.VISIBLE);
                llStep1.setVisibility(View.INVISIBLE);
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

                }
            }
        });

        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Create a response listener which listens if there is a response, and stores the response
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {//Method that triggers when response is received from the POST
                        try {
                            JSONObject jsonResponse = new JSONObject(response);//create JSONOBject called jsonResponse which contains all the data that was responded from the POST
                            boolean success = jsonResponse.getBoolean("success");//get the boolean value which is in the "success" holder IN the JSON output and put it in a boolean
                            if (success) {//if the process was successful go to login screen
                                AlertDialog.Builder builder = new AlertDialog.Builder(MyProfile.this);
                                builder.setMessage("Your details have been successfully updated!")
                                        .setNegativeButton("OK", null)
                                        .create()
                                        .show();

                            } else {
                                AlertDialog.Builder builder2 = new AlertDialog.Builder(MyProfile.this);
                                builder2.setMessage("Failed to update your details.")
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
                updateCustomer.UpdateCustomer(customerid, name, surname, email, phone, CommonMethods.sha256(password), responseListener,requestQueue);//Call the UpdateCustomer procedure and send all required data to Customer Class

            }
        });


    }
}
