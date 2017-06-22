package com.bookaholicc.ridersapp.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.bookaholicc.ridersapp.Model.Order;
import com.bookaholicc.ridersapp.R;
import com.bookaholicc.ridersapp.Utils.BundleKey;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
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
    @BindView(R.id.map_view)
    MapView mapView;
    private String TAG = "LOCATION ACTIVITY";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_activity);
        ButterKnife.bind(this);

        if (getIntent() != null) {
            String orderJson = getIntent().getStringExtra(BundleKey.ARG_ORDER);
            Gson gs = new Gson();
            order = gs.fromJson(orderJson, Order.class);
        }

        double orderLat = order.getOrderLat();
        double orderLon = order.getOrderLon();
        initMap(orderLat, orderLon);
    }

    private void initMap(double orderLat, double orderLon) {


        mapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {


        Log.d(TAG, "onMapReady: ");
    }
}
