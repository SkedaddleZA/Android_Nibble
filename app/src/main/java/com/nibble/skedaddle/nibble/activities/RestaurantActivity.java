package com.nibble.skedaddle.nibble.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.nibble.skedaddle.nibble.R;
import com.nibble.skedaddle.nibble.fragments.MainFragment;

import org.json.JSONArray;


import java.io.ByteArrayInputStream;
import java.io.InputStream;


public class RestaurantActivity  extends FragmentActivity implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks, com.google.android.gms.location.LocationListener{

    private TextView tvRestaurantDetails;
    private Button bRBooking;
    private GoogleApiClient mGoogleApiClient;
    final int PERMISSION_LOCATION = 111;
    private String[] restaurantdetails, restaurantfulldetails, customerdetails;
    private MainFragment mainFragment;
    private ImageView restImage;
    private JSONArray result;
    private RequestQueue requestQueue;
    private RelativeLayout bHome, bBookings, bProfile, rlMenu, rlContact, rlWebsite, rlReviews;
    private String url;

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
        //restaurantfulldetails = new String[15];
        final Intent restaurantintent = getIntent();
        restaurantfulldetails = restaurantintent.getStringArrayExtra("restaurantdetails");
        customerdetails = restaurantintent.getStringArrayExtra("customerdetails");
        bRBooking = findViewById(R.id.bRBooking);
        bRBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rbooking = new Intent(RestaurantActivity.this, BookingActivity.class);
                rbooking.putExtra("restaurantdetails", restaurantfulldetails);
                rbooking.putExtra("customerdetails",customerdetails);
                RestaurantActivity.this.startActivity(rbooking);
            }
        });//

        //Create Map Marker for restaurant that is chosen
        String latlng = restaurantfulldetails[4];
        String[] separated = latlng.split("#");
        double lat = Double.parseDouble(separated[0]);
        double lon = Double.parseDouble(separated[1]);
        LatLng location = new LatLng(lat, lon);//

        mainFragment.setRestaurantMarker(location);//MUST BE AFTER THE CREATION OF THE MAP right above ^

        bHome = findViewById(R.id.bHome);
        bBookings = findViewById(R.id.bBookings);
        bProfile = findViewById(R.id.bRProfile);
        rlMenu=findViewById(R.id.rlMenu);
        rlContact =findViewById(R.id.rlContact);
        rlWebsite = findViewById(R.id.rlWebsite);
        rlReviews = findViewById(R.id.rlReviews);
        //Menu Bar functions


        bHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Home = new Intent(RestaurantActivity.this, HomeActivity.class);
                Home.putExtra("customerdetails", customerdetails);
                RestaurantActivity.this.startActivity(Home);
                RestaurantActivity.this.finish();
            }
        });
        bBookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Bookings = new Intent(RestaurantActivity.this, ViewBookingsActivity.class);
                Bookings.putExtra("customerdetails", customerdetails);
                RestaurantActivity.this.startActivity(Bookings);
            }
        });
        bProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myprofile = new Intent(RestaurantActivity.this, MyProfile.class);
                myprofile.putExtra("customerdetails", customerdetails);
                RestaurantActivity.this.startActivity(myprofile);
            }
        });
        //

        rlMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menuSearch = new Intent(RestaurantActivity.this, MenuActivity.class);
                menuSearch.putExtra("customerdetails", customerdetails);
                menuSearch.putExtra("restaurantid", restaurantfulldetails[0]);
                RestaurantActivity.this.startActivity(menuSearch);

            }

        });

        rlContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + restaurantfulldetails[2]));
                startActivity(intent);
            }
        });
        rlWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                url = restaurantfulldetails[13];
                if (!url.startsWith("http://") && !url.startsWith("https://"))
                    url = "http://" + url;
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
            }
        });
        rlReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reviews = new Intent(RestaurantActivity.this, ViewReviews.class);
                reviews.putExtra("customerdetails", customerdetails);
                reviews.putExtra("restaurantdetails", restaurantfulldetails);
                RestaurantActivity.this.startActivity(reviews);
            }
        });




        //Decode Base64 string (Image) from the array and display it in ImageView
        restImage = findViewById(R.id.restImage);
        byte[] decodedString = Base64.decode(restaurantfulldetails[14],Base64.DEFAULT);
        InputStream inputStream  = new ByteArrayInputStream(decodedString);
        Bitmap bitmap  = BitmapFactory.decodeStream(inputStream);
        restImage.setImageBitmap(bitmap);

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
