package com.nibble.skedaddle.nibble.activities;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
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

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    //Declare all variables
    EditText etName,etSurname,etEmail,etPhone,etPassword,etCPassword;
    Button bRegister, bNext1, bNext2;
    RequestQueue requestQueue;
    CheckBox cbP;

    ProgressBar pb_Register;
    TextView tvLogin;

    //Dialog function is used many times so creating a method decreased amount of code
    public void messageshow(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
        builder.setMessage(message)
                .setNegativeButton("Retry", null)
                .create()
                .show();

        pb_Register.setVisibility(View.INVISIBLE);
    }

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
        bNext1 = findViewById(R.id.bNext1);
        bNext2 = findViewById(R.id.bNext2);
        cbP = findViewById(R.id.cbP);

        //Making only 1step buttons and textboxes available
        bNext2.setVisibility(View.INVISIBLE);
        bRegister.setVisibility(View.INVISIBLE);
        bNext1.setVisibility(View.VISIBLE);

        etName.setVisibility(View.VISIBLE);
        etSurname.setVisibility(View.VISIBLE);
        etEmail.setVisibility(View.INVISIBLE);
        etPhone.setVisibility(View.INVISIBLE);
        etPassword.setVisibility(View.INVISIBLE);
        etCPassword.setVisibility(View.INVISIBLE);
        cbP.setVisibility(View.INVISIBLE);


        //Button to Change screen to Login screen
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                RegisterActivity.this.startActivity(loginIntent);

            }
        });

        CommonMethods.buttonEffect(bRegister);//Button Press effect implementation


        bNext1.setOnClickListener(new View.OnClickListener() {//change screen layout from 1st step to 2nd step
            @Override
            public void onClick(View v) {

                if(etName.getText().toString().matches("")) {
                    messageshow("Please enter name.");
                }else if(etSurname.getText().toString().matches("")) {
                    messageshow("Please enter surname.");
                }else {

                    etName.setVisibility(View.INVISIBLE);
                    etSurname.setVisibility(View.INVISIBLE);
                    etEmail.setVisibility(View.VISIBLE);
                    etPhone.setVisibility(View.VISIBLE);
                    etPassword.setVisibility(View.INVISIBLE);
                    etCPassword.setVisibility(View.INVISIBLE);
                    bNext1.setVisibility(View.INVISIBLE);
                    bNext2.setVisibility(View.VISIBLE);
                    cbP.setVisibility(View.INVISIBLE);
                }
            }
        });

        bNext2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(etEmail.getText().toString().matches(""))  {
                    messageshow("Please enter email.");
                }  else if(etPhone.getText().toString().matches("")) {
                    messageshow("Please enter phone.");
                }else if(!CommonMethods.CheckEmail(etEmail.getText().toString())){
                    messageshow("Please enter correct email");
                }else if(!CommonMethods.CheckPhone(etPhone.getText().toString())) {
                    messageshow("Please enter correct phone number.");
                }else {
                    etName.setVisibility(View.INVISIBLE);
                    etSurname.setVisibility(View.INVISIBLE);
                    etEmail.setVisibility(View.INVISIBLE);
                    etPhone.setVisibility(View.INVISIBLE);
                    etPassword.setVisibility(View.VISIBLE);
                    etCPassword.setVisibility(View.VISIBLE);
                    bNext2.setVisibility(View.INVISIBLE);
                    bRegister.setVisibility(View.VISIBLE);
                    cbP.setVisibility(View.VISIBLE);
                }
            }
        });



        cbP.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked) {
                    etCPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());;
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }else{
                    etCPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    }
                }

        });


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
                if(password.matches(""))  {
                    messageshow("Please enter password.");
                }else if(!password.matches(cpassword)) {
                    messageshow("Password mismatch");
                }else if(password.length() < 8 || password.length() > 15) {
                        messageshow("Password recommended between 8 and 15 digits");

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
                                    RegisterActivity.this.finish();

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
                    insertCustomer.InsertCustomer(name, surname, email, phone, CommonMethods.sha256(password), responseListener,requestQueue);//Call the InsertCustomer procedure and send all required data to Customer Class

                }
            }


        });

    }


}
