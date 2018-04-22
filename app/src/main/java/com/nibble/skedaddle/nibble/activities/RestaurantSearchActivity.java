package com.nibble.skedaddle.nibble.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.nibble.skedaddle.nibble.R;
import com.nibble.skedaddle.nibble.fragments.Restaurant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RestaurantSearchActivity extends AppCompatActivity {
public String test;
    private ListView lvRestaurants;
    private Spinner spinner;
    private ArrayList<String> dropdown, restlist;
    private String[] restaurantdetails;
    private JSONArray result;
    private RequestQueue requestQueue;
    private TextView tvr;

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
        tvr = findViewById(R.id.tvrType);
        restaurantdetails = new String[7];

        getRestaurantTypes();



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

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

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //FIlls lvRestaurants with all items from restlist array
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

        lvRestaurants.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    JSONObject json = result.getJSONObject(position);
                    //put all selected restaurant details into an array
                    restaurantdetails[0] = Integer.toString(json.getInt("restaurantid"));
                    restaurantdetails[1] = json.getString("restaurantname");
                    restaurantdetails[2] = json.getString("address");
                    restaurantdetails[3] = json.getString("phone");
                    restaurantdetails[4] = json.getString("restauranttype");
                    restaurantdetails[5] = json.getString("gpslocation");
                    restaurantdetails[6] = json.getString("email");

                    Intent restaurant = new Intent(RestaurantSearchActivity.this, RestaurantActivity.class);
                    restaurant.putExtra("restaurantdetails",restaurantdetails);
                    RestaurantSearchActivity.this.startActivity(restaurant);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
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

