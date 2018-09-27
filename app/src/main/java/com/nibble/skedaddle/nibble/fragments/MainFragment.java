package com.nibble.skedaddle.nibble.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
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
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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
        Bitmap markerBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.mapuser);//custom bitmap
        markerBitmap = scaleBitmap(markerBitmap,70,70);//call resize method
        userMarker = new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(markerBitmap)).position(latLng).title("Me");
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
    public static Bitmap scaleBitmap(Bitmap bitmap, int newWidth, int newHeight) {//Resize a custom bitmap image
        Bitmap scaledBitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888);
        float scaleX = newWidth / (float) bitmap.getWidth();
        float scaleY = newHeight / (float) bitmap.getHeight();
        float pivotX = 0;
        float pivotY = 0;
        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(scaleX, scaleY, pivotX, pivotY);
        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bitmap, 0, 0, new Paint(Paint.FILTER_BITMAP_FLAG));
        return scaledBitmap;
    }
}
