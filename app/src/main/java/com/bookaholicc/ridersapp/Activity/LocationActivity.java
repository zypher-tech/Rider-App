package com.bookaholicc.ridersapp.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.bookaholicc.ridersapp.Model.Order;
import com.bookaholicc.ridersapp.R;
import com.bookaholicc.ridersapp.Utils.BundleKey;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 * Created by nandhu on 22/6/17.
 *
 *
 */

public class LocationActivity extends AppCompatActivity implements OnMapReadyCallback {


    Order order;

    MapView mapView;
    private String TAG = "LOCATION ACTIVITY";
    double orderLat;
    double orderLon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_activity);
        ButterKnife.bind(this);

        if (getIntent() != null) {

                orderLat = getIntent().getDoubleExtra(BundleKey.LAT,0.0);
                orderLon = getIntent().getDoubleExtra(BundleKey.LON,0.0);
        }




        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {


        // Add a marker in Sydney, Australia, and move the camera.
        LatLng sydney = new LatLng(12.971, 77.594);
        googleMap.addMarker(new MarkerOptions().position(sydney).title("New Order"));


        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15));
    }
}
