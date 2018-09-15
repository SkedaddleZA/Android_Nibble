package com.nibble.skedaddle.nibble.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.nibble.skedaddle.nibble.CommonMethods;
import com.nibble.skedaddle.nibble.classes.Customer;
import com.nibble.skedaddle.nibble.R;
import com.nibble.skedaddle.nibble.classes.SaveSharedPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;

public class LoginActivity extends AppCompatActivity {

    //Declare all variables
    EditText etEmail,etPassword;
    RequestQueue requestQueue;
    Button bLogin;
    TextView tvRegister;
    ProgressBar pbSignIn;
    private String[] customerdetails;
    private CheckBox cbAL;
    private boolean boolAutoLogin;
    private Context context;




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
        pbSignIn.setVisibility(View.VISIBLE);
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        cbAL = findViewById(R.id.cbAutoLog);

        //When tvRegister is clicked it changes the screen/window to register screen
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);

            }
        });

        CommonMethods.buttonEffect(bLogin);//Give the bLogin button the ability to appear as if it is pressed(Calls from CommonMethods class

        context = getApplicationContext();

        if(SaveSharedPreference.getbAL(context).matches("true"))
        {
            pbSignIn.setVisibility(View.VISIBLE);
            boolAutoLogin = true;
            cbAL.setChecked(true);
        }else {
            pbSignIn.setVisibility(View.INVISIBLE);
            boolAutoLogin = false;
            cbAL.setChecked(false);
        }

        customerdetails = new String[6];

        if(SaveSharedPreference.getUserName(LoginActivity.this).length() == 0)
        {
            // call Login Activity
        }
        else
        {
            pbSignIn.setVisibility(View.VISIBLE);
            String username = SaveSharedPreference.getUserName(context);
            String pass = SaveSharedPreference.getPassWord(context);
            etEmail.setText(username);
            etPassword.setText("*********");

            login(username, pass);
        }


        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pbSignIn.setVisibility(View.VISIBLE);
                final String email = etEmail.getText().toString();
                final String password = CommonMethods.sha256(etPassword.getText().toString());

                login(email,password);

            }
        });

        cbAL.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    boolAutoLogin = true;
                }else{
                    boolAutoLogin = false;

                }
            }

        });



    }

    public void login(final String email, final String pass){

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
                        customerdetails[0] = Integer.toString(customerid);
                        customerdetails[1] = name;
                        customerdetails[2] = surname;
                        customerdetails[3] = email;
                        customerdetails[4] = phone;
                        customerdetails[5] = pass;

                        SaveSharedPreference.setbAL(context, "false");
                        if(boolAutoLogin) {
                            SaveSharedPreference.setUserName(context, email);
                            SaveSharedPreference.setPassWord(context, pass);
                            SaveSharedPreference.setbAL(context, "true");
                        }else {
                            SaveSharedPreference.setbAL(context, "false");
                            SaveSharedPreference.clearUserName(context);
                        }

                        Intent Home = new Intent(LoginActivity.this, HomeActivity.class);
                        Home.putExtra("customerdetails",customerdetails);
                        LoginActivity.this.startActivity(Home);
                        LoginActivity.this.finish();
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
        loginCustomer.LoginCustomer(email, pass, responseListener, requestQueue);


    }

}
