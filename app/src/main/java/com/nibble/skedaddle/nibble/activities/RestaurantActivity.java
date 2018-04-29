package com.nibble.skedaddle.nibble.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.nibble.skedaddle.nibble.R;
import com.nibble.skedaddle.nibble.fragments.MainFragment;


public class RestaurantActivity  extends FragmentActivity implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks, com.google.android.gms.location.LocationListener{

    private TextView tvRestaurantDetails;
    private Button bRBooking;
    private GoogleApiClient mGoogleApiClient;
    final int PERMISSION_LOCATION = 111;
    private MainFragment mainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build(); //creates locations api and builds it

        //Uses the MainFragment(MapFragment) to create a map from the FrameLayout on the Layout Screen
        mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.container_main);
        if (mainFragment == null) {
            mainFragment = MainFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.container_main, mainFragment).commit();
        }//


        //used to go to create booking request screen
        final Intent restaurant = getIntent();
        final String[] restaurantdetails = restaurant.getStringArrayExtra("restaurantdetails");
        final String[] customerdetails = restaurant.getStringArrayExtra("customerdetails");
        bRBooking = findViewById(R.id.bRBooking);
        tvRestaurantDetails = findViewById(R.id.tvRestaurantDetails);
        tvRestaurantDetails.setText(restaurantdetails[0] + " " + restaurantdetails[1] + " " + restaurantdetails[2]);
        bRBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rbooking = new Intent(RestaurantActivity.this, BookingActivity.class);
                rbooking.putExtra("restaurantdetails", restaurantdetails);
                rbooking.putExtra("customerdetails",customerdetails);
                RestaurantActivity.this.startActivity(rbooking);
            }
        });//

        //Create Map Marker for restaurant that is chosen
        String latlng = restaurantdetails[5];
        String[] separated = latlng.split("#");
        double lat = Double.parseDouble(separated[0]);
        double lon = Double.parseDouble(separated[1]);
        LatLng location = new LatLng(lat, lon);//

        mainFragment.setRestaurantMarker(location);//MUST BE AFTER THE CREATION OF THE MAP right above ^


        ImageView bHome = findViewById(R.id.bHome);
        bHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Home = new Intent(RestaurantActivity.this, HomeActivity.class);
                Home.putExtra("customerdetails", customerdetails);
                RestaurantActivity.this.startActivity(Home);
                RestaurantActivity.this.finish();

            }
        });



    }
    //Interface inherited methods
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

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
    public void onLocationChanged(Location location) {
        Log.v("Donkey", "Long: " + location.getLatitude() + " Lat: " + location.getLatitude());
        mainFragment.setUserMarker(new LatLng(location.getLatitude(), location.getLongitude()));
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
