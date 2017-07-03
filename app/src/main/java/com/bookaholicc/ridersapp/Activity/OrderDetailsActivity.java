package com.bookaholicc.ridersapp.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bookaholicc.ridersapp.Adapters.ProductListAdapter;
import com.bookaholicc.ridersapp.DataStore.DataStore;
import com.bookaholicc.ridersapp.Model.MiniProduct;
import com.bookaholicc.ridersapp.Model.Order;
import com.bookaholicc.ridersapp.Network.AppRequestQueue;
import com.bookaholicc.ridersapp.R;
import com.bookaholicc.ridersapp.Utils.APIUtils;
import com.bookaholicc.ridersapp.Utils.BundleKey;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nandhu on 22/6/17.
 *
 */

public class OrderDetailsActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {
    @BindView(R.id.od_oid)
    TextView mOrderId;
    @BindView(R.id.od_oname)
    TextView mOrderName;
    @BindView(R.id.od_phonenumber)
    TextView mPhoneNumber;
    @BindView(R.id.od_amount)
    TextView mAmount;
    private String TAG = "ORDER";



    @BindView(R.id.dl_button) Button mLocationButton;

    @BindView(R.id.order_details_products_list)
    RecyclerView mListview;

    @BindView(R.id.delivered_button)
    Button mButton;


    @BindView(R.id.order_det_root)
    RelativeLayout mRoot;


    @BindView(R.id.order_details_root)
    FrameLayout mRootFrame;
    Order or = null;

    ProgressDialog mProgressDialog = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_details_activity);
        ButterKnife.bind(this);


        if (getIntent() != null) {
            String orderJson = getIntent().getStringExtra(BundleKey.ARG_ORDER);
            Gson gs = new Gson();
            or = gs.fromJson(orderJson, Order.class);
        }
        else{
            throw  new NullPointerException("Needs Order Id");
        }

        Log.d(TAG, "onCreate: Recevied Order " + or.getOrderId());

        List<MiniProduct> mList = or.getmList();

        ProductListAdapter mAdapter = new ProductListAdapter(this, mList);

        mListview.setAdapter(mAdapter);

        mListview.setLayoutManager(new LinearLayoutManager(this));


        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deliveredButton();
            }
        });


        mLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLocationPage(or.getOrderLat(),or.getOrderLon());
            }
        });

    }

    private void showLocationPage(double orderLat, double orderLon) {
        Intent i = new Intent(this,LocationActivity.class);
        i.putExtra(BundleKey.LAT,orderLat);
        i.putExtra(BundleKey.LON,orderLon);
        startActivity(i);


    }

    private void deliveredButton() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(OrderDetailsActivity.this);

        // Setting Dialog Title
        alertDialog.setTitle("Confirm Delete...");

        // Setting Dialog Message
        alertDialog.setMessage("Are you sure Product is Delivered");

        // Setting Icon to Dialog


        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {

                // Write your code here to invoke YES event
                changeOrderStatus();

            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke NO event

                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();

    }


    private void showProgressView() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Changing Order Status");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.show();
    }

    private void hideProgresDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
        }
    }

    private void changeOrderStatus() {



        showProgressView();
        int orderId = or.getOrderId();
        String riderId = null;
        String phoneNumber = null;
        String riderName = null;
        DataStore mStore = DataStore.getStorageInstance(this);
        if (mStore != null) {
            riderId = mStore.getRiderId();
            riderName = mStore.getUserName();
            phoneNumber  = mStore.getPhoneNumber();
        }


        try {


            // Build the Request body
            JSONObject mJsonObject = new JSONObject();
            if (mJsonObject != null) {
                mJsonObject.put(APIUtils.RIDER_ID, riderId);
                mJsonObject.put(APIUtils.RIDER_NAME,riderName);
                mJsonObject.put(APIUtils.ORDER_ID, orderId);
                mJsonObject.put(APIUtils.PHONE_NUMBER, phoneNumber);
            }

            JsonObjectRequest mJsonObjectRequest = new JsonObjectRequest(Request.Method.POST, APIUtils.CHANGE_DELIVERY_STATUS, mJsonObject, this, this);

            AppRequestQueue mAppRequestQueue = AppRequestQueue.getInstance(this);

            mAppRequestQueue.addToRequestQue(mJsonObjectRequest);
        } catch (Exception e) {
            Log.d(TAG, "changeOrderStatus: " + e.getLocalizedMessage());
        }
    }


    @Override
    public void onResponse(JSONObject response) {
        hideProgresDialog();
        try {
            int b = response.getInt(APIUtils.DELIVERY_STATUS);
            if (b==1){
                //Delivered
                showOrderFulfilled();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showOrderFulfilled() {

    }

    private void showFailedAlert() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(OrderDetailsActivity.this);

        // Setting Dialog Title
        alertDialog.setTitle("Error!");

        // Setting Dialog Message
        alertDialog.setMessage("Please Check you have an Active Internet Connection");

        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                dialog.dismiss();


            }
        });



        // Showing Alert Message
        alertDialog.show();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        hideProgresDialog();
        Log.d(TAG, "onErrorResponse: Exception " + error.getLocalizedMessage());
        showFailedAlert();
    }
}
