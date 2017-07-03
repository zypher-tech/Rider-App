package com.bookaholicc.ridersapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bookaholicc.ridersapp.DataStore.DataStore;
import com.bookaholicc.ridersapp.MainActivity;
import com.bookaholicc.ridersapp.Network.AppRequestQueue;
import com.bookaholicc.ridersapp.R;
import com.bookaholicc.ridersapp.Utils.APIUtils;
import com.bookaholicc.ridersapp.Utils.StringValidator;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nandhu on 3/7/17.
 *
 */

public class SignUpActivity extends AppCompatActivity implements Response.ErrorListener, Response.Listener<JSONObject> {


    @BindView(R.id.name_edit_text)
    TextInputEditText mName;
    @BindView(R.id.phone_text)
    TextInputEditText mPhoneNumber;
    @BindView(R.id.email_text)
    TextInputEditText mEmail;
    @BindView(R.id._pass_text)
    TextInputEditText mPassword;
    @BindView(R.id.sign_up_button)
    Button mSignUpButton;
    private String TAG = "SIGNUP";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        ButterKnife.bind(this);

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
    }

    private void register() {
        Log.d(TAG, "register: inside");
        String name = mName.getText().toString();
        String email = mEmail.getText().toString();
        String phone = mPhoneNumber.getText().toString();
        String pass = mPassword.getText().toString();
        if (StringValidator.CheckUserName(name)) {
            if (StringValidator.checkeEMail(email)) {
                if (StringValidator.checkPhoneNumber(phone)) {
                    if (StringValidator.checkPassword(pass)) {
                        registerRider(name, email, phone, pass);

                    } else {
                        // Wrong Pass
                        Toast.makeText(this, "Enter Valid Name", Toast.LENGTH_LONG).show();
                    }
                } else {
                    //Wrong Phone Nmber
                    Toast.makeText(this, "Enter Valid PhoneNumber", Toast.LENGTH_LONG).show();
                }
            } else {
                // Wrong Email
                Toast.makeText(this, "Enter Valid Email", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(this, "Enter Valid Name", Toast.LENGTH_LONG).show();
        }

    }

    private void registerRider(String name, String email, String phone, String pass) {

        Log.d(TAG, "Making Request");
        JSONObject mJsonObject = new JSONObject();
        try {
            mJsonObject.put(APIUtils.RIDER_NAME, name);
            mJsonObject.put(APIUtils.RIDER_EMAIL, email);
            mJsonObject.put(APIUtils.PASSWORD, pass);
            mJsonObject.put(APIUtils.PHONE_NUMBER, phone);
            JsonObjectRequest mRegisterRequest = new JsonObjectRequest(Request.Method.POST, APIUtils.REGISTER_RIDER_API, mJsonObject, this, this);
            AppRequestQueue mAppRequestQueue = AppRequestQueue.getInstance(this);
            mAppRequestQueue.addToRequestQue(mRegisterRequest);
        } catch (Exception e) {
            Log.d(TAG, "registerRider: ");
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d(TAG, "onErrorResponse: "+error.getLocalizedMessage());
    }

    @Override
    public void onResponse(JSONObject response) {
        Log.d(TAG, "onResponse: "+response.toString());
        try {


            if (response.getInt(APIUtils.STATUS) == 0) {
                //Unsuccessfull
                Toast.makeText(this, "Failed to Register", Toast.LENGTH_LONG).show();
            } else {
                String name = response.getString(APIUtils.RIDER_NAME);
                String email = response.getString(APIUtils.RIDER_EMAIL);
                String phoneNmber = response.getString(APIUtils.PHONE_NUMBER);
                int riderId = response.getInt(APIUtils.RIDER_ID);

                DataStore mDataStore = DataStore.getStorageInstance(this);
                mDataStore.setUserName(name);
                mDataStore.saveRiderId(riderId);
                mDataStore.setEmailId(email);
                mDataStore.savePhoneNumber(phoneNmber);
                mDataStore.setIsFirstTime(false);
                startActivity(new Intent(this, MainActivity.class));
            }
        } catch (Exception e) {

            Log.d(TAG, "onResponse: Error in parsing response "+e.getLocalizedMessage());
        }
    }

}