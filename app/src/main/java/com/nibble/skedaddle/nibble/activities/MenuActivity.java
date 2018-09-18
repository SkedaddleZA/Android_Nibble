package com.nibble.skedaddle.nibble.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.nibble.skedaddle.nibble.CustomListAdapters.RestList;
import com.nibble.skedaddle.nibble.CustomModels.RestaurantModel;
import com.nibble.skedaddle.nibble.R;
import com.nibble.skedaddle.nibble.classes.Restaurant;
import com.nibble.skedaddle.nibble.classes.MenuItems;
import com.nibble.skedaddle.nibble.classes.MenuCategory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.Locale;

import static com.nibble.skedaddle.nibble.R.layout.activity_menu;

public class MenuActivity extends AppCompatActivity {
    private ListView lvMenus;
    private Spinner spinner;
    private ArrayList<String> dropdown;
    private ArrayList<RestaurantModel> itemmodel;
    private String[] menudetails, customerdetails;
    private JSONArray result;
    private RequestQueue requestQueue;
    private TextView tvm;
    private RelativeLayout bHome, bBookings, bProfile;
    private NumberFormat format = NumberFormat.getCurrencyInstance(Locale.getDefault());
    private String restaurantid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_menu);

        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        final Intent menuSearch = getIntent();
        customerdetails = menuSearch.getStringArrayExtra("customerdetails");
        restaurantid = menuSearch.getStringExtra("restaurantid");


        requestQueue = Volley.newRequestQueue(getApplicationContext());
        format.setCurrency((Currency.getInstance("ZAR")));
        dropdown = new ArrayList<>();
        itemmodel = new ArrayList<>();
        spinner = findViewById(R.id.sMTypes);
        lvMenus = findViewById(R.id.lvMenus);
        bHome = findViewById(R.id.bHome);
        bBookings = findViewById(R.id.bBookings);
        bProfile = findViewById(R.id.bProfile);

        //menudetails = new String[3];

        getMenuCategory();
        bHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Home = new Intent(MenuActivity.this, HomeActivity.class);
                Home.putExtra("customerdetails", customerdetails);
                MenuActivity.this.startActivity(Home);
                MenuActivity.this.finish();
            }
        });
        bBookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Bookings = new Intent(MenuActivity.this, ViewBookingsActivity.class);
                Bookings.putExtra("customerdetails", customerdetails);
                MenuActivity.this.startActivity(Bookings);
            }
        });
        bProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myprofile = new Intent(MenuActivity.this, MyProfile.class);
                myprofile.putExtra("customerdetails", customerdetails);
                MenuActivity.this.startActivity(myprofile);
            }
        });
        //
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final String itematpos=parent.getItemAtPosition(position).toString();
                itemmodel.clear();

                if(itematpos.matches("Specials")) {

                    //specialcode - GetSpecialItems(RestaurantID, Weekday, listener, requestqueue);
                    String weekday = "Monday";
                    Calendar calendar = Calendar.getInstance();
                    int day = calendar.get(Calendar.DAY_OF_WEEK);

                    switch (day) {
                        case Calendar.SUNDAY:
                            weekday = "Sunday";
                            break;
                            // Current day is Sunday
                        case Calendar.MONDAY:
                            weekday = "Monday";
                            break;
                            // Current day is Monday
                        case Calendar.TUESDAY:
                            weekday = "Tuesday";
                            break;
                            // Current day is Tuesday
                        case Calendar.WEDNESDAY:
                            weekday = "Wednesday";
                            break;
                            // Current day is Wednesday
                        case Calendar.THURSDAY:
                            weekday = "Thursday";
                            break;
                            // Current day is Thursday
                        case Calendar.FRIDAY:
                            weekday = "Friday";
                            break;
                            // Current day is Friday
                        case Calendar.SATURDAY:
                            weekday = "Saturday";
                            break;
                            // Current day is Saturday
                    }

                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            JSONObject j;
                            try {
                                j = new JSONObject(response);
                                result = j.getJSONArray("menuitems");
                                    for (int i = 0; i < result.length(); i++) {
                                        try {
                                            JSONObject json = result.getJSONObject(i);
                                            itemmodel.add(new RestaurantModel(json.getString("itemname"), format.format(Double.parseDouble(json.getString("itemprice")))));

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            //FIlls lvRestaurants with all items from restlist array
                            lvMenus.setAdapter(new RestList(itemmodel, getApplicationContext()));


                        }
                    };
                    MenuItems menuItems = new MenuItems();
                    menuItems.GetSpecialItems(weekday, restaurantid, responseListener, requestQueue);

                }else {

                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            JSONObject j;
                            try {
                                j = new JSONObject(response);
                                result = j.getJSONArray("menuitems");
                                   for (int i = 0; i < result.length(); i++) {
                                       try {
                                           JSONObject json = result.getJSONObject(i);
                                           itemmodel.add(new RestaurantModel(json.getString("itemname"), format.format(Double.parseDouble(json.getString("itemprice")))));

                                       } catch (JSONException e) {
                                           e.printStackTrace();
                                       }
                                   }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            //FIlls lvRestaurants with all items from restlist array
                            lvMenus.setAdapter(new RestList(itemmodel, getApplicationContext()));


                        }
                    };
                    MenuItems menuItems = new MenuItems();
                    menuItems.GetMenuInfo(itematpos, restaurantid, responseListener, requestQueue);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Another interface callback
            }
        });
    }
    private void getMenuCategory(){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject j;
                try {
                    j = new JSONObject(response);
                    result = j.getJSONArray("names");
                    for(int i=0;i<result.length();i++){
                        try {
                            JSONObject json = result.getJSONObject(i);
                            dropdown.add(json.getString("name"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    //dropdown.add("Specials"); IF there were not specials category in menucategory table
                    spinner.setAdapter(new ArrayAdapter<>(MenuActivity.this, android.R.layout.simple_spinner_dropdown_item, dropdown));

                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        };
        MenuCategory menuCategory = new MenuCategory();
        menuCategory.GetMenuCategory(restaurantid, responseListener, requestQueue);
    }
}
