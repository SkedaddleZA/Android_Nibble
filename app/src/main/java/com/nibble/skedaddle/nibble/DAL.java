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

    String result;
    String insertUrl = "http://chrismb2gun.heliohost.org/registerCustomer.php";
    public String InsertCustomer(final String FirstName, final String LastName, final String Email, final String Phone, final String Password, RequestQueue requestQueue)
    {

        StringRequest request = new StringRequest(Request.Method.POST, insertUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                         result = "success";
                    } else {
                         result = "fail";
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
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
        requestQueue.add(request);
        return result;
    }
    }



