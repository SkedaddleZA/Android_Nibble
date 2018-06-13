package com.nibble.skedaddle.nibble.activities;

import android.content.Intent;
import android.graphics.Color;
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
import com.nibble.skedaddle.nibble.R;
import com.nibble.skedaddle.nibble.classes.Restaurant;
import com.nibble.skedaddle.nibble.classes.MenuItems;
import com.nibble.skedaddle.nibble.classes.MenuCategory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;

import static com.nibble.skedaddle.nibble.R.layout.activity_menu;

public class MenuActivity extends AppCompatActivity {
    private ListView lvMenus;
    private Spinner spinner;
    private ArrayList<String> dropdown, restlist;
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
        restlist = new ArrayList<>();
        spinner = findViewById(R.id.sMTypes);
        lvMenus = findViewById(R.id.lvMenus);
        bHome = findViewById(R.id.bHome);
        bBookings = findViewById(R.id.bBookings);

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
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final String itematpos=parent.getItemAtPosition(position).toString();
                restlist.clear();
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j;
                        try {
                            j = new JSONObject(response);
                            result = j.getJSONArray("menuitems");
                            for(int i=0;i<result.length();i++){
                                try {
                                    JSONObject json = result.getJSONObject(i);
                                    restlist.add(json.getString("itemname") + ", " + format.format(Double.parseDouble(json.getString("itemprice"))));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //FIlls lvRestaurants with all items from restlist array
                        lvMenus.setAdapter(new ArrayAdapter<String>(MenuActivity.this, android.R.layout.simple_list_item_1, restlist) {
                            @Override
                            public View getView(int position, View convertView, ViewGroup parent) {
                                // Get the Item from ListView
                                View view = super.getView(position, convertView, parent);

                                // Initialize a TextView for ListView each Item
                                TextView tv = (TextView) view.findViewById(android.R.id.text1);

                                // Set the text color of TextView (ListView Item)
                                tv.setTextColor(Color.BLACK);

                                // Generate ListView Item using TextView
                                return view;
                            }
                        });


                    }
                };
                MenuItems menuItems = new MenuItems();
                menuItems.GetMenuInfo(itematpos, restaurantid, responseListener, requestQueue);


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
