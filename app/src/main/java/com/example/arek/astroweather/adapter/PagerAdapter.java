package com.example.arek.astroweather.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.arek.astroweather.fragments.BasicDataFragment;
import com.example.arek.astroweather.fragments.ExtralDataFragment;
import com.example.arek.astroweather.fragments.MoonFragment;
import com.example.arek.astroweather.fragments.SunFragment;
import com.example.arek.astroweather.fragments.ForecastFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {

    public PagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Forecast";

            case 1:
                return "Moon";

            case 2:
                return "Sun";

            case 3:
                return "Basic";

            default:
                return super.getPageTitle(position);
        }
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new ForecastFragment();
            case 1:
                return new MoonFragment();
            case 2:
                return new SunFragment();
            case 3:
                return new BasicDataFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}