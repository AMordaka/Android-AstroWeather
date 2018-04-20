package com.example.arek.astroweather.fragments;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.arek.astroweather.R;
import com.example.arek.astroweather.astroweather.AstroWeatherConfig;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class SunFragment extends Fragment {

    private AstroWeatherConfig astroWeatherConfig;
    private TextView timer;
    private Handler mHandler;
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
        initTime(view);
        initTextViews(view);
        contentView();
        return view;
    }

    void initTime(View view) {
        timer = (TextView) view.findViewById(R.id.time);
        this.mHandler = new Handler();
        this.mHandler.postDelayed(m_Runnable, 2000);
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
        this.astroWeatherConfig = new AstroWeatherConfig();
        String temp = null;
        this.latitude.setText(String.valueOf(astroWeatherConfig.getLocation().getLatitude()));
        this.longitude.setText(String.valueOf(astroWeatherConfig.getLocation().getLongitude()));
        temp = String.valueOf(astroWeatherConfig.getAstroCalculator().getSunInfo().getSunrise().getHour()) + " " + String.valueOf(astroWeatherConfig.getAstroCalculator().getSunInfo().getSunrise().getMinute() + " " + String.valueOf(astroWeatherConfig.getAstroCalculator().getSunInfo().getSunrise().getSecond()));
        this.sunrise.setText(temp);
        temp = String.valueOf(astroWeatherConfig.getAstroCalculator().getSunInfo().getSunset().getHour()) + " " + String.valueOf(astroWeatherConfig.getAstroCalculator().getSunInfo().getSunset().getMinute() + " " + String.valueOf(astroWeatherConfig.getAstroCalculator().getSunInfo().getSunset().getSecond()));
        this.sunset.setText(temp);
        this.azimuthSet.setText(String.valueOf(round(astroWeatherConfig.getAstroCalculator().getSunInfo().getAzimuthSet(), 2)));
        this.azimuthRise.setText(String.valueOf(round(astroWeatherConfig.getAstroCalculator().getSunInfo().getAzimuthRise(), 2)));
        temp = String.valueOf(astroWeatherConfig.getAstroCalculator().getSunInfo().getTwilightEvening().getHour()) + " " + String.valueOf(astroWeatherConfig.getAstroCalculator().getSunInfo().getTwilightEvening().getMinute() + " " + String.valueOf(astroWeatherConfig.getAstroCalculator().getSunInfo().getTwilightEvening().getSecond()));
        this.twilightEvening.setText(temp);
        temp = String.valueOf(astroWeatherConfig.getAstroCalculator().getSunInfo().getTwilightMorning().getHour()) + " " + String.valueOf(astroWeatherConfig.getAstroCalculator().getSunInfo().getTwilightMorning().getMinute() + " " + String.valueOf(astroWeatherConfig.getAstroCalculator().getSunInfo().getTwilightMorning().getSecond()));
        this.twilightMorning.setText(temp);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    private final Runnable m_Runnable = new Runnable() {
        public void run() {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            timer.setText(dateFormat.format(Calendar.getInstance(TimeZone.getDefault()).getTime()));
            SunFragment.this.mHandler.postDelayed(m_Runnable, 1000);
        }
    };


}
