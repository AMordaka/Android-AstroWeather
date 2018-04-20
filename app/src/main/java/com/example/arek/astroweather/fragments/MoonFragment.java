package com.example.arek.astroweather.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.arek.astroweather.MainActivity;
import com.example.arek.astroweather.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class MoonFragment extends Fragment {

    private TextView timer;
    private Handler mHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_moon, container, false);
        initTime(view);
        return view;
    }

    void initTime(View view){
        timer = (TextView) view.findViewById(R.id.time);
        this.mHandler = new Handler();
        this.mHandler.postDelayed(m_Runnable, 2000);
    }

    private final Runnable m_Runnable = new Runnable() {
        public void run()
        {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            timer.setText(dateFormat.format(Calendar.getInstance(TimeZone.getDefault()).getTime()));
            MoonFragment.this.mHandler.postDelayed(m_Runnable, 1000);
        }
    };
}