package com.nibble.skedaddle.nibble;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class RestaurantSearchActivity extends AppCompatActivity {
public String test;

    private Spinner spinner;
    private ArrayList<String> students;
    private JSONArray result;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_search);

        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        final Intent restaurantSearch = getIntent();
        final int customerid = restaurantSearch.getIntExtra("customerid", 1);
        requestQueue = Volley.newRequestQueue(getApplicationContext());


        //

        students = new ArrayList<>();
        spinner = findViewById(R.id.sRTypes);
        //spinner.setOnItemSelectedListener();
        getData();
    }

    private void getData(){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j;
                        try {
                            j = new JSONObject(response);
                            result = j.getJSONArray("restauranttypes");
                            getRestaurantTypes(result);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
        Customer loginCustomer = new Customer();
        loginCustomer.GetRestaurantTypes(responseListener, requestQueue);
    }

    private void getRestaurantTypes(JSONArray j){
        for(int i=0;i<j.length();i++){
            try {
                JSONObject json = j.getJSONObject(i);
                students.add(json.getString("restauranttype"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        spinner.setAdapter(new ArrayAdapter<>(RestaurantSearchActivity.this, android.R.layout.simple_spinner_dropdown_item, students));
    }
}

