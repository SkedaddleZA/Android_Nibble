package com.nibble.skedaddle.nibble;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
    private ListView lvRestaurants;
    private Spinner spinner;
    private ArrayList<String> dropdown, restlist;
    private JSONArray result;
    private RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_search);

        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        final Intent restaurantSearch = getIntent();
        final String[] customerdetails = restaurantSearch.getStringArrayExtra("customerdetails");
        requestQueue = Volley.newRequestQueue(getApplicationContext());


        //

        //

        dropdown = new ArrayList<>();
        restlist = new ArrayList<>();
        spinner = findViewById(R.id.sRTypes);
        lvRestaurants = findViewById(R.id.lvRestaurants);

        getRestaurantTypes();
        final TextView tvrType = findViewById(R.id.tvrType);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //tvrType.setText("Spinner selected : ");
                //tvrType.setText(tvrType.getText() + parent.getItemAtPosition(position).toString());

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j;
                        try {
                            j = new JSONObject(response);
                            result = j.getJSONArray("restaurants");
                            for(int i=0;i<result.length();i++){
                                try {
                                    JSONObject json = result.getJSONObject(i);
                                    restlist.add(json.getString("restaurantname"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
//additems to LISTVIEW

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        lvRestaurants.setAdapter(new ArrayAdapter<>(RestaurantSearchActivity.this, android.R.layout.simple_list_item_1, restlist));


                    }
                };
                Restaurant restaurants = new Restaurant();
                restaurants.GetRestaurants(parent.getItemAtPosition(position).toString(),responseListener, requestQueue);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Another interface callback
            }
        });



    }

    private void getRestaurantTypes(){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j;
                        try {
                            j = new JSONObject(response);
                            result = j.getJSONArray("restauranttypes");
                            for(int i=0;i<result.length();i++){
                                try {
                                    JSONObject json = result.getJSONObject(i);
                                    dropdown.add(json.getString("restauranttype"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            spinner.setAdapter(new ArrayAdapter<>(RestaurantSearchActivity.this, android.R.layout.simple_spinner_dropdown_item, dropdown));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                    }
                };
        Restaurant restauranttype = new Restaurant();
        restauranttype.GetRestaurantTypes(responseListener, requestQueue);
    }


}

