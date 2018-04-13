package com.nibble.skedaddle.nibble;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    //Declare all variables
    EditText etName,etSurname,etEmail,etPhone,etPassword,etCPassword;
    Button bRegister;
    RequestQueue requestQueue;

    ProgressBar pb_Register;
    TextView tvLogin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);//Change screen transition method

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);//Prevent keyboard opening on activity launch

        //Link GUI elements to variables
        tvLogin = findViewById(R.id.tvLogin);
        etName =  findViewById(R.id.etName);
        etSurname =  findViewById(R.id.etSurname);
        etEmail =  findViewById(R.id.etEmail);
        etPhone =  findViewById(R.id.etPhone);
        etPassword =  findViewById(R.id.etPassword);
        etCPassword =  findViewById(R.id.etCPassword);
        bRegister =  findViewById(R.id.bRegister);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        pb_Register = findViewById(R.id.pb_Register);

        //Button to Change screen to Login screen
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                RegisterActivity.this.startActivity(loginIntent);

            }
        });

        CommonMethods.buttonEffect(bRegister);//Button Press effect implementation

        //Button Register click event
        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb_Register.setVisibility(View.VISIBLE);
                String name = etName.getText().toString();
                String surname = etSurname.getText().toString();
                String email = etEmail.getText().toString();
                String phone = etPhone.getText().toString();
                String password = etPassword.getText().toString();
                String cpassword = etCPassword.getText().toString();
                //Simple input not null/empty check
                if(name.matches("")) {
                    messageshow("Please enter name.");
                }else if(surname.matches("")) {
                    messageshow("Please enter surname.");
                }else if(email.matches(""))  {
                    messageshow("Please enter email.");
                }  else if(phone.matches(""))  {
                    messageshow("Please enter phone.");
                }else if(password.matches(""))  {
                    messageshow("Please enter password.");
                }else if(!password.matches(cpassword)){
                    messageshow("Password mismatch");

                    }else{
                    //Create a response listener which listens if there is a response, and stores the response
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {//Method that triggers when response is received from the POST
                            try {
                                JSONObject jsonResponse = new JSONObject(response);//create JSONOBject called jsonResponse which contains all the data that was responded from the POST
                                boolean success = jsonResponse.getBoolean("success");//get the boolean value which is in the "success" holder IN the JSON output and put it in a boolean
                                pb_Register.setVisibility(View.INVISIBLE);
                                if (success) {//if the process was successful go to login screen
                                    Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    RegisterActivity.this.startActivity(loginIntent);


                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
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

                    Customer insertCustomer = new Customer();//Create Customer Object
                    insertCustomer.InsertCustomer(name, surname, email, phone, password, responseListener,requestQueue);//Call the InsertCustomer procedure and send all required data to Customer Class

                }
            }

            //Dialog function is used many times so creating a method decreased amount of code
            public void messageshow(String message){
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setMessage(message)
                        .setNegativeButton("Retry", null)
                        .create()
                        .show();

                pb_Register.setVisibility(View.INVISIBLE);
            }
        });

    }
}
