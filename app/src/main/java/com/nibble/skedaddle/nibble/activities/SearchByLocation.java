package com.nibble.skedaddle.nibble.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.model.LatLng;
import com.nibble.skedaddle.nibble.CustomListAdapters.RestList;
import com.nibble.skedaddle.nibble.CustomModels.RestaurantModel;
import com.nibble.skedaddle.nibble.R;
import com.nibble.skedaddle.nibble.classes.Restaurant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class SearchByLocation extends FragmentActivity implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks, com.google.android.gms.location.LocationListener {

    private ListView lvRestaurants;
    private Spinner spinner;
    private ArrayList<String> dropdown;
    private ArrayList<RestaurantModel> restmodel;
    private String[] restaurantdetails, restaurantfulldetails, customerdetails;
    private float[] distances = new float[3];
    private ArrayList<Double> distancesArray = new ArrayList<>();
    private ArrayList<Integer> orderofresult = new ArrayList<>();
    private JSONArray result, restresult;
    private RequestQueue requestQueue;
    private TextView tvr;
    private ProgressBar pbLoadRest;
    private RelativeLayout bHome, bBookings, bProfile;
    private int countSuburb = -2;
    private String mySuburb = "";
    private double latitude, longitude;
    private GoogleApiClient mGoogleApiClient;
    final int PERMISSION_LOCATION = 111;
    private String rating="1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_location);

        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        final Intent locationSearch = getIntent();
        customerdetails = locationSearch.getStringArrayExtra("customerdetails");
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        bHome = findViewById(R.id.bHome);
        bBookings = findViewById(R.id.bBookings);
        bProfile = findViewById(R.id.bRProfile);
        dropdown = new ArrayList<>();
        restmodel = new ArrayList<>();
        spinner = findViewById(R.id.sRTypes);
        lvRestaurants = findViewById(R.id.lvRestaurants);
        tvr = findViewById(R.id.tvrType);
        restaurantdetails = new String[3];
        restaurantfulldetails = new String[15];
        pbLoadRest = findViewById(R.id.pb_loadrest);
        pbLoadRest.setVisibility(View.INVISIBLE);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build(); //creates locations api and builds it



        //Menu Bar functions
        bHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Home = new Intent(SearchByLocation.this, HomeActivity.class);
                Home.putExtra("customerdetails", customerdetails);
                SearchByLocation.this.startActivity(Home);
                SearchByLocation.this.finish();
            }
        });
        bBookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Bookings = new Intent(SearchByLocation.this, ViewBookingsActivity.class);
                Bookings.putExtra("customerdetails", customerdetails);
                SearchByLocation.this.startActivity(Bookings);
                SearchByLocation.this.finish();
            }
        });
        bProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myprofile = new Intent(SearchByLocation.this, MyProfile.class);
                myprofile.putExtra("customerdetails", customerdetails);
                SearchByLocation.this.startActivity(myprofile);
                SearchByLocation.this.finish();
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

        lvRestaurants.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    JSONObject json = result.getJSONObject(orderofresult.get(position));
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
    private void getLocations(final String mySuburbName){
        dropdown.clear();//IT ARE NOT MAKE SENSE THAT IT IS REQUIRED HERE BUT NOT IN SearchByType
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject j;
                try {
                    j = new JSONObject(response);
                    result = j.getJSONArray("locations");
                    for(int i=0;i<result.length();i++){
                        try {
                            JSONObject json = result.getJSONObject(i);
                            String suburb = json.getString("suburbname");
                            if(!suburb.matches("")) {
                                if (suburb.matches(mySuburbName)) {
                                    countSuburb = i;
                                }
                            }
                            dropdown.add(suburb);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    spinner.setAdapter(new ArrayAdapter<>(SearchByLocation.this, android.R.layout.simple_spinner_dropdown_item, dropdown));
                    if (countSuburb != -2)
                    spinner.setSelection(countSuburb);
                    else if(countSuburb == -2) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(SearchByLocation.this);
                            builder.setMessage("There are no restaurants in your suburb.")
                                    .setNegativeButton("OK", null)
                                    .create()
                                    .show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        };
        Restaurant restloc = new Restaurant();
        restloc.GetLocations(responseListener, requestQueue);
    }

    private void FillListView(String itematposition){
        restmodel.clear();
        distancesArray.clear();
        orderofresult.clear();
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

                            if (!json.isNull("gpslocation")) {

                            //convert string coords to location object
                            String latlng = json.getString("gpslocation");
                            String[] separated = latlng.split("#");
                            double lat = Double.parseDouble(separated[0]);
                            double lon = Double.parseDouble(separated[1]);
                            //

                            Location.distanceBetween(lat, lon, latitude, longitude, distances);
                            String x = Float.toString(distances[0]);
                            distancesArray.add(Double.parseDouble(x));

                            String abrev;
                            if (Double.parseDouble(x) > 999) {
                                float roundedKM = Math.round(Float.parseFloat(x)) / 1000;
                                abrev = "km";
                                x = Float.toString(roundedKM);
                            } else {
                                float roundedM = Math.round(Float.parseFloat(x) * 10) / 10;
                                abrev = "m";
                                x = Float.toString(roundedM);
                            }
                            if (json.isNull("avgrating"))
                                rating = "0";
                            else
                                rating = Integer.toString(json.getInt("avgrating"));
                            restmodel.add(new RestaurantModel(json.getString("restaurantname"), x + " " + abrev, rating));
                            orderofresult.add(i);
                        }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    for(int i=0;i<distancesArray.size() -1;i++){
                        for(int k= i+1;k<distancesArray.size();k++){
                           if(distancesArray.get(k) < distancesArray.get(i)){
                               double temp = distancesArray.get(i);
                               distancesArray.set(i,distancesArray.get(k));
                               distancesArray.set(k,temp);

                               RestaurantModel stemp = restmodel.get(i);
                               restmodel.set(i,restmodel.get(k));
                               restmodel.set(k,stemp);

                               int itemp = orderofresult.get(i);
                               orderofresult.set(i,orderofresult.get(k));
                               orderofresult.set(k,itemp);
                           }

                        }

                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //FIlls lvRestaurants with all items from restlist array
                lvRestaurants.setAdapter(new RestList(restmodel,getApplicationContext()));

            }
        };
        Restaurant restaurants = new Restaurant();
        restaurants.GetRestaurantsByLocation(itematposition,responseListener, requestQueue);


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
                            Intent restaurantintent = new Intent(SearchByLocation.this, RestaurantActivity.class);
                            restaurantintent.putExtra("restaurantdetails",restaurantfulldetails);
                            restaurantintent.putExtra("customerdetails",customerdetails);
                            SearchByLocation.this.startActivity(restaurantintent);

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


    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        countSuburb = -2;

        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {

            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();

            String[] arrArray = address.split("\\s*,\\s*");
            mySuburb = arrArray[1];
        } catch (IOException e) {
            e.printStackTrace();
        }

        getLocations(mySuburb);


    }
    public void startLocationServices() {
        Log.v("Donkey", "Starting Location Services called");
        try {
            LocationRequest req = LocationRequest.create().setPriority(LocationRequest.PRIORITY_LOW_POWER);
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, req, this);
            Log.v("Donkey", "Requesting Location Updates");

        } catch (SecurityException exception) {
            Log.v("Donkey", exception.toString());
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_LOCATION);
            Log.v("Donkey", "Requesting Permissions");
        } else {
            startLocationServices();
            Log.v("Donkey", "Starting Location Services from onConnected");
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSION_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startLocationServices();
                    Log.v("Donkey", "Permission Granted - Starting Services");
                } else {
                    Log.v("Donkey", "Permission DENIED");
                }
            }
        }

    }



}
