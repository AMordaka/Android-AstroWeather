package com.example.arek.astroweather.fragments;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arek.astroweather.R;
import com.example.arek.astroweather.SettingsActivity;
import com.example.arek.astroweather.astroweather.AstroCallback;
import com.example.arek.astroweather.astroweather.AstroWeatherConfig;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class SunFragment extends Fragment implements AstroCallback {

    private static final String LATITUDE = "Latitude";
    private static final String LONGITUDE = "Longitude";
    private static final String REFRESH_DATA = "Refresh Data";

    private AstroWeatherConfig astroWeatherConfig;
    private TextView latitude;
    private TextView longitude;
    private TextView sunrise;
    private TextView azimuthRise;
    private TextView sunset;
    private TextView azimuthSet;
    private TextView twilightEvening;
    private TextView twilightMorning;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sun, container, false);
        astroWeatherConfig = AstroWeatherConfig.getAstroWeatherInstance();
        astroWeatherConfig.registerForUpdates(this);
        initTextViews(view);
        contentView();
        return view;
    }

    void initTextViews(View view) {
        latitude = (TextView) view.findViewById(R.id.latitude);
        longitude = (TextView) view.findViewById(R.id.longitude);
        sunrise = (TextView) view.findViewById(R.id.sunrise);
        azimuthRise = (TextView) view.findViewById(R.id.azimuthrise);
        sunset = (TextView) view.findViewById(R.id.sunset);
        azimuthSet = (TextView) view.findViewById(R.id.azimuthset);
        twilightEvening = (TextView) view.findViewById(R.id.twilightevening);
        twilightMorning = (TextView) view.findViewById(R.id.twilightmorning);
    }

    void contentView() {
        String temp = null;
        this.latitude.setText(String.valueOf(astroWeatherConfig.getLocation().getLatitude()));
        this.longitude.setText(String.valueOf(astroWeatherConfig.getLocation().getLongitude()));
        temp = String.valueOf(astroWeatherConfig.getAstroCalculator().getSunInfo().getSunrise().getHour()) + ":" + String.valueOf(astroWeatherConfig.getAstroCalculator().getSunInfo().getSunrise().getMinute());
        this.sunrise.setText(temp);
        temp = String.valueOf(astroWeatherConfig.getAstroCalculator().getSunInfo().getSunset().getHour()) + ":" + String.valueOf(astroWeatherConfig.getAstroCalculator().getSunInfo().getSunset().getMinute());
        this.sunset.setText(temp);
        this.azimuthSet.setText(String.valueOf(round(astroWeatherConfig.getAstroCalculator().getSunInfo().getAzimuthSet(), 2)));
        this.azimuthRise.setText(String.valueOf(round(astroWeatherConfig.getAstroCalculator().getSunInfo().getAzimuthRise(), 2)));
        temp = String.valueOf(astroWeatherConfig.getAstroCalculator().getSunInfo().getTwilightEvening().getHour()) + ":" + String.valueOf(astroWeatherConfig.getAstroCalculator().getSunInfo().getTwilightEvening().getMinute());
        this.twilightEvening.setText(temp);
        temp = String.valueOf(astroWeatherConfig.getAstroCalculator().getSunInfo().getTwilightMorning().getHour()) + ":" + String.valueOf(astroWeatherConfig.getAstroCalculator().getSunInfo().getTwilightMorning().getMinute());
        this.twilightMorning.setText(temp);
        //Toast.makeText(getActivity(), REFRESH_DATA, Toast.LENGTH_SHORT).show();
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    @Override
    public void onSettingsUpdate() {
        contentView();
    }

    public void onDestroy() {
        astroWeatherConfig.unregisterForUpdates(this);
        super.onDestroy();
    }

}
