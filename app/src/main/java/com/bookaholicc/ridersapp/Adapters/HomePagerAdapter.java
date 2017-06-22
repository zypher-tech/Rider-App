package com.bookaholicc.ridersapp.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.bookaholicc.ridersapp.Fragments.OrdersFragment;
import com.bookaholicc.ridersapp.Fragments.ReturnsFragment;

/**
 * Created by nandhu on 21/6/17.
 *  Returns Two Fragments from now
 */

public class HomePagerAdapter extends FragmentStatePagerAdapter {



    public HomePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case  0:
                return new OrdersFragment();
            case 1:
                return new ReturnsFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
