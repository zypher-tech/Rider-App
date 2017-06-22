package com.bookaholicc.ridersapp.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.bookaholicc.ridersapp.Model.Order;
import com.bookaholicc.ridersapp.R;
import com.bookaholicc.ridersapp.Utils.BundleKey;
import com.google.gson.Gson;

/**
 * Created by nandhu on 22/6/17.
 *
 */

public class OrderDetailsActivity extends AppCompatActivity{
    private String TAG = "ORDER";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_details_activity);

        Order or = null;

        if (getIntent()!=null){
            String orderJson = getIntent().getStringExtra(BundleKey.ARG_ORDER);
            Gson gs  = new Gson();
            or = gs.fromJson(orderJson,Order.class);
        }

        Log.d(TAG, "onCreate: Recevied Order "+or.getOrderId());



    }



}
