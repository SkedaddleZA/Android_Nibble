package com.nibble.skedaddle.nibble.fragments;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nibble.skedaddle.nibble.R;

import static android.content.Context.LOCATION_SERVICE;


public class MainFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private MarkerOptions userMarker;
    private LatLng LAT_LNG;// = new LatLng(-33.9800224, 25.552754100000016);

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        // Inflate the layout for this fragment

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return view;
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
       mMap = googleMap;
        mMap.addMarker(new MarkerOptions().position(LAT_LNG).title("Restaurant"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(LAT_LNG));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(13.0f));
    }
    public void setUserMarker(LatLng latLng) {
        Log.v("Donkey", "Set User Location");
        if (userMarker == null) {
        userMarker = new MarkerOptions().position(latLng).title("Me");
        mMap.addMarker(userMarker);

        }
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

    }

    public void setRestaurantMarker(LatLng latLng) {
       LAT_LNG = latLng;
    /*    LatLng restaurant = new LatLng(-33.9800224, 25.552754100000016);
        mMap.addMarker(new MarkerOptions().position(restaurant).title("Restaurant"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(restaurant));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(13.0f));*/
    }



}
