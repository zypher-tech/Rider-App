package com.bookaholicc.ridersapp.service;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bookaholicc.ridersapp.DataStore.DataStore;
import com.bookaholicc.ridersapp.Network.AppRequestQueue;
import com.bookaholicc.ridersapp.Utils.APIUtils;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

/**
 * Created by nandhu on 22/6/17.
 *
 */

public class FirebaseTokenService extends FirebaseInstanceIdService implements Response.ErrorListener, Response.Listener<JSONObject> {

    private String TAG = "RIDER_TOKEN";

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        int userId = 0;

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        DataStore mStore = DataStore.getStorageInstance(getApplicationContext());
        if (mStore != null){
            userId = mStore.getRiderId();
        }

        if (userId != 0 && refreshedToken != null){
            sendTokenToServer(userId,refreshedToken);
        }
    }


    /**  APIUtils.SAVE_TOKEN_API accepts 3 Arguments
     *
     *          "profileId"(userId)
     *          "tokenId"
     *          "fromWhere" --> Indicating where to save this token
     *
     * */
    private void sendTokenToServer(int userId, String refreshedToken) {

        JSONObject mObject = new JSONObject();
        try {
            mObject.put(APIUtils.PROFILE_ID,userId);
            mObject.put(APIUtils.TOKEN_ID,refreshedToken);
            /*This is 2 for RIders */
            mObject.put(APIUtils.FROM_WHERE,"2");
        } catch (JSONException e) {
            Log.d(TAG, "sendTokenToServer: Exception : "+e.getLocalizedMessage());
        }

        JsonObjectRequest mRequest = new JsonObjectRequest(Request.Method.POST, APIUtils.SAVE_TOKEN_API,mObject,this,this);

        AppRequestQueue mRequestQueue = AppRequestQueue.getInstance(getApplicationContext());
        mRequestQueue.addToRequestQue(mRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        DataStore mStore  = DataStore.getStorageInstance(getApplicationContext());
        mStore.setTokenSaved(false);
    }

    @Override
    public void onResponse(JSONObject response) {
        DataStore mStore  = DataStore.getStorageInstance(getApplicationContext());
        try {
            if (Objects.equals(response.getString("isAccepted"),"1")){
                //Token Save Successfully
                mStore.setTokenSaved(true);
                mStore.saveToken(response.getString("tokenId"));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
