package com.bookaholicc.ridersapp.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bookaholicc.ridersapp.Adapters.ProductListAdapter;
import com.bookaholicc.ridersapp.DataStore.DataStore;
import com.bookaholicc.ridersapp.Model.MiniProduct;
import com.bookaholicc.ridersapp.Model.Order;
import com.bookaholicc.ridersapp.Model.PriceInfo;
import com.bookaholicc.ridersapp.Model.Product;
import com.bookaholicc.ridersapp.Network.AppRequestQueue;
import com.bookaholicc.ridersapp.R;
import com.bookaholicc.ridersapp.Utils.APIUtils;
import com.bookaholicc.ridersapp.Utils.BundleKey;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;

/**
 * Created by nandhu on 22/6/17.
 *
 */

public class OrderDetailsActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {
    private String TAG = "ORDER";


    @BindView(R.id.order_details_products_list)
    RecyclerView mListview;

    @BindView(R.id.delivered_button)
    Button mButton;


    @BindView(R.id.order_details_root)
    FrameLayout mRootFrame;
    Order or =null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_details_activity);



        if (getIntent()!=null){
            String orderJson = getIntent().getStringExtra(BundleKey.ARG_ORDER);
            Gson gs  = new Gson();
            or = gs.fromJson(orderJson,Order.class);
        }

        Log.d(TAG, "onCreate: Recevied Order "+or.getOrderId());
        List<MiniProduct> mList = or.getmList();

        ProductListAdapter mAdapter = new ProductListAdapter(this,mList);

        mListview.setAdapter(mAdapter);

        mListview.setLayoutManager(new LinearLayoutManager(this));


        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deliveredButton();
            }
        });


    }

    private void deliveredButton() {
        // Show Alert Dialog
        // Ask For Confirmation


    }

    private  void changeOrderStatus(){
            String userId = or.getUserId();
            String orderId = or.getOrderId();
            String riderId = null;
        DataStore mStore = DataStore.getStorageInstance(this);
        if (mStore != null){
             riderId = mStore.getRiderId();
        }


        try {


            // Build the Request body
            JSONObject mJsonObject = new JSONObject();
            if (mJsonObject != null) {
                mJsonObject.put(APIUtils.RIDER_ID, riderId);
                mJsonObject.put(APIUtils.ORDER_ID, orderId);
                mJsonObject.put(APIUtils.USER_ID, or.getUserId());
            }

            JsonObjectRequest mJsonObjectRequest = new JsonObjectRequest(Request.Method.POST, APIUtils.CHANGE_DELIVERY_STATUS, mJsonObject, this, this);

            AppRequestQueue mAppRequestQueue = AppRequestQueue.getInstance(this);

            mAppRequestQueue.addToRequestQue(mJsonObjectRequest);
        }
        catch (Exception e){
            Log.d(TAG, "changeOrderStatus: "+e.getLocalizedMessage());
        }
    }


    @Override
    public void onResponse(JSONObject response) {
        try {
            boolean b  = response.getBoolean(APIUtils.DELIVERY_STATUS);
            if (b){
                mRootFrame.setBackgroundColor(ContextCompat.getColor(this,R.color.material_green400));
            }
            else{

                showFailedAlert();
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showFailedAlert() {

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d(TAG, "onErrorResponse: Exception "+error.getLocalizedMessage());
        showFailedAlert();
    }
}
