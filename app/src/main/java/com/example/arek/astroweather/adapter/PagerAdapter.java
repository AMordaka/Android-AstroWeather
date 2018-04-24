package com.example.arek.astroweather.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.example.arek.astroweather.fragments.MoonFragment;
import com.example.arek.astroweather.fragments.SunFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {

    public PagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Sun";

            case 1:
                return "Moon";

            default:
                return super.getPageTitle(position);
        }
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new SunFragment();
            case 1:
                return new MoonFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}