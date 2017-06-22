package com.bookaholicc.ridersapp.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bookaholicc.ridersapp.Activity.LocationActivity;
import com.bookaholicc.ridersapp.Activity.OrderDetailsActivity;
import com.bookaholicc.ridersapp.Adapters.OrderAdapter;
import com.bookaholicc.ridersapp.DataStore.DataStore;
import com.bookaholicc.ridersapp.Model.MiniProduct;
import com.bookaholicc.ridersapp.Model.Order;
import com.bookaholicc.ridersapp.Network.AppRequestQueue;
import com.bookaholicc.ridersapp.R;
import com.bookaholicc.ridersapp.Utils.APIUtils;
import com.bookaholicc.ridersapp.Utils.BundleKey;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.android.volley.Request.Method;

/**
 * Created by nandhu on 21/6/17.
 * The Main Page Orders Are bathced Up and shown here
 */


public class OrdersFragment extends Fragment implements Response.ErrorListener, Response.Listener<JSONObject>,OrderAdapter.OrderCallback {

    @BindView(R.id.progress_view)
    ProgressBar progressView;
    @BindView(R.id.orders_loading_view)
    RelativeLayout ordersLoadingView;
    private Context mContext = null;
    private String TAG = "ORDERS";

    @BindView(R.id.orders_root_frame)
    FrameLayout mRootFrame;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (mContext == null) {
            mContext = context;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.orders_fragment, container, false);
        ButterKnife.bind(this, view);



        try {
            getOrders();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mContext != null) {
            mContext = null;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void getOrders() throws Exception {
        JSONObject mObject = new JSONObject();
        DataStore mStore = DataStore.getStorageInstance(mContext);

        String riderId = mStore.getRiderId();
        mObject.put(APIUtils.RIDER_ID, riderId);
        JsonObjectRequest mRequest = new JsonObjectRequest(Method.POST, APIUtils.RIDER_GET_ORDERS_API, mObject, this, this);
        AppRequestQueue mAppRequestQueue = AppRequestQueue.getInstance(mContext);
        mAppRequestQueue.addToRequestQue(mRequest);

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d(TAG, "onErrorResponse: " + error.getLocalizedMessage());
    }


    /*Got the Response a List of Orders to Fulfill*/
    @Override
    public void onResponse(JSONObject response) {


        List<Order> mOrdersList = parseResponse(response);
        showOrders(mOrdersList);
    }

    private void showOrders(List<Order> mOrdersList) {


        View v = null;
        try {
            if (mRootFrame != null ){

                mRootFrame.removeAllViews();
                v = LayoutInflater.from(mContext).inflate(R.layout.recycler_view,mRootFrame);

            }
        }
        catch (Exception e){
            Log.d(TAG, "showOrders: Exception in Inflating VIew "+e.getLocalizedMessage());
        }

        RecyclerView mListView = (RecyclerView) v.findViewById(R.id.rv_list);
        mListView.setLayoutManager(new LinearLayoutManager((mContext)));
        OrderAdapter mAdapter = new OrderAdapter(mOrdersList,mContext,this);

    }

    private List<Order> parseResponse(JSONObject response) {


        try {

            List<Order> mList = new ArrayList<>();
            JSONArray mResp = response.getJSONArray("orders");
            for (int i = 0; i < mResp.length(); i++) {
                JSONObject mObject = mResp.getJSONObject(i);
                String userId = mObject.getString(APIUtils.USER_ID);
                String userName = mObject.getString(APIUtils.FIRST_NAME);
                String orderId = mObject.getString(APIUtils.ORDER_ID);
                double orderLat = mObject.getDouble(APIUtils.ORDER_LAT);
                double orderLon = mObject.getDouble(APIUtils.ORDER_LON);
                String phoneNumber = mObject.getString(APIUtils.ORDER_ID);
                String amount = mObject.getString(APIUtils.AMOUNT);
                String Address = mObject.getString(APIUtils.ORDER_ID);
                List<MiniProduct> mProduct = parseProducts(mObject.getJSONArray(APIUtils.PRODUCTS_KEYWORD));
                mList.add(new Order(mProduct, amount, phoneNumber, orderLat, orderLon, userName, orderId, userId));

            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in Parsing Response  " + e.getLocalizedMessage());
        }
        return null;
    }


    private List<MiniProduct> parseProducts(JSONArray mArray) {
        try {

            List<MiniProduct> mlist = new ArrayList<>(mArray.length());
            for (int i = 0; i < mArray.length(); i++) {
                JSONObject mObject = mArray.getJSONObject(i);
                String pName = mObject.getString(APIUtils.PRODUCT_NAME);
                String pid = mObject.getString(APIUtils.PID);
                String imageURL = mObject.getString(APIUtils.IMAGE_URL);
                mlist.add(new MiniProduct(pName, imageURL, pid));
            }
            return mlist;

        } catch (Exception e) {
            Log.d(TAG, "parseProducts: Exception " + e.getLocalizedMessage());
        }
        return null;
    }



    /*Pass the Order Details to Details Page*/
    @Override
    public void seeDetails(Order order) {
        Log.d(TAG, "seeDetails: "+order.getFirstName());
        Intent i = new Intent(mContext,OrderDetailsActivity.class);
        Gson gs = new Gson();
        String orderJson = gs.toJson(order);
        i.putExtra(BundleKey.ARG_ORDER,orderJson);
        startActivity(i);
    }

    @Override
    public void seeLocation(Order order) {

        Intent i = new Intent(mContext,LocationActivity.class);
        Gson gs = new Gson();
        String orderJson = gs.toJson(order);
        i.putExtra(BundleKey.ARG_ORDER,orderJson);
        startActivity(i);

    }
}
