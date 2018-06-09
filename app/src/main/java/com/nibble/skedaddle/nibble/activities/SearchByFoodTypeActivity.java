package com.nibble.skedaddle.nibble.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
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
    private ArrayList<String> dropdown, restlist;
    private String[] restaurantdetails, customerdetails;
    private JSONArray result;
    private RequestQueue requestQueue;
    private TextView tvf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_food_type);

        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        final Intent foodSearch = getIntent();
        customerdetails = foodSearch.getStringArrayExtra("customerdetails");
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        ImageView bHome = findViewById(R.id.bHome);
        dropdown = new ArrayList<>();
        restlist = new ArrayList<>();
        spinner = findViewById(R.id.sFTypes);
        lvFood = findViewById(R.id.lvFood);
        tvf = findViewById(R.id.tvfType);
        restaurantdetails = new String[3];

        getFoodTypes();

        bHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Home = new Intent(SearchByFoodTypeActivity.this, HomeActivity.class);
                Home.putExtra("customerdetails", customerdetails);
                SearchByFoodTypeActivity.this.startActivity(Home);
                SearchByFoodTypeActivity.this.finish();


            }
        });

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
                                    restlist.add(json.getString("restaurantname") + ", " + json.getString("suburbname")+ "\t " +json.getString("date") );

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //FIlls lvRestaurants with all items from restlist array
                        lvFood.setAdapter(new ArrayAdapter<String>(SearchByFoodTypeActivity.this, android.R.layout.simple_list_item_1, restlist) {
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
                Restaurant restaurant = new Restaurant();
                restaurant.GetRestaurantsByFoodType(parent.getItemAtPosition(position).toString(),responseListener, requestQueue);


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
                    JSONObject json = result.getJSONObject(position);
                    //put all selected restaurant details into an array
                    restaurantdetails[0] = Integer.toString(json.getInt("foodtypeid"));
                    //restaurantdetails[1] = json.getString("restaurantname");


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });


    }

    private void getFoodTypes(){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject j;
                try {
                    j = new JSONObject(response);
                    result = j.getJSONArray("typename");
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



}
