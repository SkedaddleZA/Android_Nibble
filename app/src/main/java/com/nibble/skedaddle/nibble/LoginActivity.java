package com.nibble.skedaddle.nibble;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    //Declare all variables
    EditText etEmail,etPassword;
    RequestQueue requestQueue;
    Button bLogin;
    TextView tvRegister;
    ProgressBar pbSignIn;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);//Prevent keyboard opening on activity launch

        overridePendingTransition(R.anim.islide_in, R.anim.islide_out);//Simply change the Transition method of screens

        //Link all used GUI elements to a variable
        tvRegister = findViewById(R.id.tvRegister);
        bLogin = findViewById(R.id.bLogin);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        pbSignIn = findViewById(R.id.pb_SignIn);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        pbSignIn.setVisibility(View.INVISIBLE);

        //When tvRegister is clicked it changes the screen/window to register screen
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);

            }
        });

        CommonMethods.buttonEffect(bLogin);//Give the bLogin button the ability to appear as if it is pressed(Calls from CommonMethods class

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pbSignIn.setVisibility(View.VISIBLE);
                final String email = etEmail.getText().toString();
                final String password = etPassword.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            pbSignIn.setVisibility(View.INVISIBLE);
                            if (success) {

                                final int customerid = jsonResponse.getInt("customerid");
                                final String name = jsonResponse.getString("firstname");
                                final String surname = jsonResponse.getString("lastname");
                                final String phone = jsonResponse.getString("phone");

                                String[] customerdetails = {Integer.toString(customerid),name,surname,email,phone,password} ;

                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                intent.putExtra("customerdetails",customerdetails);
                                LoginActivity.this.startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("Login Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                Customer loginCustomer = new Customer();
                loginCustomer.LoginCustomer(email, password, responseListener, requestQueue);

            }
        });

    }
}
