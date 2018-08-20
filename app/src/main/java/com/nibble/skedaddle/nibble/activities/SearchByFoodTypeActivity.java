package com.nibble.skedaddle.nibble.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.nibble.skedaddle.nibble.CustomListAdapters.RestList;
import com.nibble.skedaddle.nibble.CustomModels.RestaurantModel;
import com.nibble.skedaddle.nibble.R;
import com.nibble.skedaddle.nibble.classes.FoodType;
import com.nibble.skedaddle.nibble.classes.Restaurant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchByFoodTypeActivity extends AppCompatActivity {
    public String test;
    private ListView lvFood;
    private Spinner spinner;
    private ArrayList<String> dropdown;
    private ArrayList<RestaurantModel> restmodel;
    private String[] restaurantdetails, restaurantfulldetails, customerdetails;
    private JSONArray result, restresult;
    private RequestQueue requestQueue;
    private TextView tvf;
    private ProgressBar pbLoadRest;
    private RelativeLayout bHome, bBookings, bProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_food_type);

        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        final Intent foodSearch = getIntent();
        customerdetails = foodSearch.getStringArrayExtra("customerdetails");
        requestQueue = Volley.newRequestQueue(getApplicationContext());


        //

        //
        bHome = findViewById(R.id.bHome);
        bBookings = findViewById(R.id.bBookings);
        bProfile = findViewById(R.id.bRProfile);
        dropdown = new ArrayList<>();
        restmodel = new ArrayList<>();
        spinner = findViewById(R.id.sFTypes);
        lvFood = findViewById(R.id.lvFood);
        tvf = findViewById(R.id.tvfType);
        restaurantdetails = new String[3];
        restaurantfulldetails = new String[15];
        pbLoadRest = findViewById(R.id.pb_loadrest);
        pbLoadRest.setVisibility(View.INVISIBLE);
        getFoodType();

        //Menu Bar functions
        bHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Home = new Intent(SearchByFoodTypeActivity.this, HomeActivity.class);
                Home.putExtra("customerdetails", customerdetails);
                SearchByFoodTypeActivity.this.startActivity(Home);
                SearchByFoodTypeActivity.this.finish();
            }
        });
        bBookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Bookings = new Intent(SearchByFoodTypeActivity.this, ViewBookingsActivity.class);
                Bookings.putExtra("customerdetails", customerdetails);
                SearchByFoodTypeActivity.this.startActivity(Bookings);
            }
        });
        bProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myprofile = new Intent(SearchByFoodTypeActivity.this, MyProfile.class);
                myprofile.putExtra("customerdetails", customerdetails);
                SearchByFoodTypeActivity.this.startActivity(myprofile);
            }
        });
        //

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final String itematposition = parent.getItemAtPosition(position).toString();
                FillListView(itematposition);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Another interface callback
            }
        });

        lvFood.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {

                    //Animation for clicking on item
                    Animation animation1 = new AlphaAnimation(0.3f, 1.0f);
                    animation1.setDuration(4000);
                    view.startAnimation(animation1);

                    JSONObject json = result.getJSONObject(position);
                    //put all selected restaurant details into an array
                    restaurantdetails[0] = Integer.toString(json.getInt("restaurantid"));
                    //restaurantdetails[1] = json.getString("restaurantname");

                    pbLoadRest.setVisibility(View.VISIBLE); //Due to non instant loading
                    GotoRestaurant();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });


    }

    private void getFoodType(){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject j;
                try {
                    j = new JSONObject(response);
                    result = j.getJSONArray("typenames");
                    for(int i=0;i<result.length();i++){
                        try {
                            JSONObject json = result.getJSONObject(i);
                            dropdown.add(json.getString("typename"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    spinner.setAdapter(new ArrayAdapter<>(SearchByFoodTypeActivity.this, android.R.layout.simple_spinner_dropdown_item, dropdown));

                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        };
        FoodType foodType = new FoodType();
        foodType.GetFoodTypes(responseListener, requestQueue);
    }

    private void GotoRestaurant() {
        //Start fetch restaurant details on click
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject j;
                try {
                    j = new JSONObject(response);
                    restresult = j.getJSONArray("restaurant");
                    for(int i=0;i<restresult.length();i++){
                        try {
                            JSONObject json = restresult.getJSONObject(i);
                            restaurantfulldetails[0] = Integer.toString(json.getInt("restaurantid"));
                            restaurantfulldetails[1] = json.getString("restaurantname");
                            restaurantfulldetails[2] = json.getString("phone");
                            restaurantfulldetails[3] = json.getString("restauranttype");
                            restaurantfulldetails[4] = json.getString("gpslocation");
                            restaurantfulldetails[5] = json.getString("email");
                            restaurantfulldetails[6] = json.getString("addressline1");
                            restaurantfulldetails[7] = json.getString("addressline2");
                            restaurantfulldetails[8] = json.getString("suburbname");
                            restaurantfulldetails[9] = json.getString("postalcode");
                            restaurantfulldetails[10] = json.getString("cityname");
                            restaurantfulldetails[11] = json.getString("opentime");
                            restaurantfulldetails[12] = json.getString("closetime");
                            restaurantfulldetails[13] = json.getString("websiteurl");
                            restaurantfulldetails[14] = json.getString("logo");

                            pbLoadRest.setVisibility(View.INVISIBLE); //Due to non instant loading
                            Intent restaurantintent = new Intent(SearchByFoodTypeActivity.this, RestaurantActivity.class);
                            restaurantintent.putExtra("restaurantdetails",restaurantfulldetails);
                            restaurantintent.putExtra("customerdetails",customerdetails);
                            SearchByFoodTypeActivity.this.startActivity(restaurantintent);
                            //RestaurantSearchActivity.this.finish();//THIS IS A WORKAROUND TO NOT KNOWING HOW TO REFRESH A PREVIOUS SCREEN WHEN ANDROID BACK BUTTON PRESSED
                            //FIXED USING DIFFEREN JSON OBJECT
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        Restaurant restaurantobject = new Restaurant();
        restaurantobject.GetRestaurantDetails(restaurantdetails[0], responseListener, requestQueue);
        //end of fetch






    }


    private void FillListView(String itematposition){
        restmodel.clear();
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
                            restmodel.add(new RestaurantModel(json.getString("restaurantname"), json.getString("suburbname")));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //FIlls lvRestaurants with all items from restlist array
                lvFood.setAdapter(new RestList(restmodel,getApplicationContext()));


            }
        };
        Restaurant restaurants = new Restaurant();
        restaurants.GetRestaurantsByFoodType(itematposition,responseListener, requestQueue);


    }


}

