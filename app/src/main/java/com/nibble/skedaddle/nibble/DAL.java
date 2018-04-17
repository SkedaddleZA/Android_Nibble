package com.nibble.skedaddle.nibble;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;

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

/**
 * Created by s216431174 on 2018/04/13.
 */

class DAL {
    //declaration of global variables

    boolean result;
    String[] arrloginResponse= new String[4];
    private static final String insertUrl = "http://SICT-IIS.nmmu.ac.za/skedaddle/registerCustomer.php";
    private static final String loginUrl = "http://SICT-IIS.nmmu.ac.za/skedaddle/loginCustomer.php";
   //Insert customer procedure
    public void InsertCustomer(final String FirstName, final String LastName, final String Email, final String Phone, final String Password, Response.Listener<String> listener, RequestQueue requestQueue)
    {
        StringRequest request = new StringRequest(Request.Method.POST, insertUrl, listener, new Response.ErrorListener() {//Create a StringRequest which POSTS data to the database then records the response in listener variable
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("firstname", FirstName);
                parameters.put("lastname", LastName);
                parameters.put("email", Email);
                parameters.put("phone", Phone);
                parameters.put("password", Password);

                return parameters;

            }
        };
        requestQueue.add(request);//Runs the request which POSTS the data
    }

    public void LoginCustomer(final String Email, final String Password, Response.Listener<String> listener, RequestQueue requestQueue)
    {
        StringRequest request = new StringRequest(Request.Method.POST, loginUrl, listener ,new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("email", Email);
                parameters.put("password", Password);

                return parameters;

            }

        };
        requestQueue.add(request);
    }
}



