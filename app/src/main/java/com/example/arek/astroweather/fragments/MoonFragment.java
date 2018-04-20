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

public class MoonFragment extends Fragment {

    private AstroWeatherConfig astroWeatherConfig;
    private TextView timer;
    private Handler mHandler;
    private TextView latitude;
    private TextView longitude;
    private TextView moonrise;
    private TextView moonset;
    private TextView newMoon;
    private TextView fullMoon;
    private TextView lunarPhase;
    private TextView dayMonthLuar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_moon, container, false);
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
        moonrise = (TextView) view.findViewById(R.id.moonrise);
        moonset = (TextView) view.findViewById(R.id.moonset);
        newMoon = (TextView) view.findViewById(R.id.newmoon);
        fullMoon = (TextView) view.findViewById(R.id.fullmoon);
        lunarPhase = (TextView) view.findViewById(R.id.lunarphase);
        dayMonthLuar = (TextView) view.findViewById(R.id.daylunar);
    }

    void contentView() {
        this.astroWeatherConfig = new AstroWeatherConfig();
        String temp = null;
        this.latitude.setText(String.valueOf(astroWeatherConfig.getLocation().getLatitude()));
        this.longitude.setText(String.valueOf(astroWeatherConfig.getLocation().getLongitude()));
        temp = String.valueOf(astroWeatherConfig.getAstroCalculator().getMoonInfo().getMoonrise().getHour()) + " " + String.valueOf(astroWeatherConfig.getAstroCalculator().getMoonInfo().getMoonrise().getMinute()) + " " + String.valueOf(astroWeatherConfig.getAstroCalculator().getMoonInfo().getMoonrise().getSecond());
        this.moonrise.setText(temp);
        temp = String.valueOf(astroWeatherConfig.getAstroCalculator().getMoonInfo().getMoonset().getHour()) + " " + String.valueOf(astroWeatherConfig.getAstroCalculator().getMoonInfo().getMoonset().getMinute()) + " " + String.valueOf(astroWeatherConfig.getAstroCalculator().getMoonInfo().getMoonset().getSecond());
        this.moonset.setText(temp);
        temp = String.valueOf(astroWeatherConfig.getAstroCalculator().getMoonInfo().getNextNewMoon().getDay() + " " + astroWeatherConfig.getAstroCalculator().getMoonInfo().getNextNewMoon().getMonth() + " " + astroWeatherConfig.getAstroCalculator().getMoonInfo().getNextNewMoon().getYear());
        this.newMoon.setText(temp);
        temp = String.valueOf(astroWeatherConfig.getAstroCalculator().getMoonInfo().getNextFullMoon().getDay() + " " + astroWeatherConfig.getAstroCalculator().getMoonInfo().getNextFullMoon().getMonth() + " " + astroWeatherConfig.getAstroCalculator().getMoonInfo().getNextFullMoon().getYear());
        this.fullMoon.setText(temp);
        this.lunarPhase.setText(String.valueOf(round(astroWeatherConfig.getAstroCalculator().getMoonInfo().getIllumination(), 2)));
        this.dayMonthLuar.setText(String.valueOf((int) astroWeatherConfig.getAstroCalculator().getMoonInfo().getAge()));
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
            MoonFragment.this.mHandler.postDelayed(m_Runnable, 1000);
        }
    };
}