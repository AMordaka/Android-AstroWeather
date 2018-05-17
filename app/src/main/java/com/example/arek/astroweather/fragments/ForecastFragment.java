package com.example.arek.astroweather.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.arek.astroweather.R;
import com.example.arek.astroweather.data.Condition;
import com.example.arek.astroweather.data.Units;


public class ForecastFragment extends Fragment {

    private ImageView weatherIconImageView;
    private TextView dayLabelTextView;
    private TextView highTemperatureTextView;
    private TextView lowTemperatureTextView;

    private ImageView weatherIconImageView1;
    private TextView dayLabelTextView1;
    private TextView highTemperatureTextView1;
    private TextView lowTemperatureTextView1;

    public ForecastFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forecast, container, false);

        weatherIconImageView = (ImageView) view.findViewById(R.id.weatherIconImageView);
        dayLabelTextView = (TextView) view.findViewById(R.id.dayTextView);
        highTemperatureTextView = (TextView) view.findViewById(R.id.highTemperatureTextView);
        lowTemperatureTextView = (TextView) view.findViewById(R.id.lowTemperatureTextView);

        weatherIconImageView1 = (ImageView) view.findViewById(R.id.weatherIconImageView1);
        dayLabelTextView1 = (TextView) view.findViewById(R.id.dayTextView1);
        highTemperatureTextView1 = (TextView) view.findViewById(R.id.highTemperatureTextView1);
        lowTemperatureTextView1 = (TextView) view.findViewById(R.id.lowTemperatureTextView1);

        return view;
    }

    public void loadForecast(Condition[] forecast, Units units) {
        int weatherIconImageResource = getResources().getIdentifier("icon_" + forecast[0].getCode(), "drawable", getActivity().getPackageName());

        weatherIconImageView.setImageResource(weatherIconImageResource);
        dayLabelTextView.setText(forecast[0].getDay());
        highTemperatureTextView.setText(getString(R.string.temperature_output, forecast[0].getHighTemperature(), units.getTemperature()));
        lowTemperatureTextView.setText(getString(R.string.temperature_output, forecast[0].getLowTemperature(), units.getTemperature()));

        int i = 1;
            int weatherIconImageResourceOther = getResources().getIdentifier("icon_" + forecast[i].getCode(), "drawable", getActivity().getPackageName());

            weatherIconImageView1.setImageResource(weatherIconImageResourceOther);
            dayLabelTextView1.setText(forecast[i].getDay());
            highTemperatureTextView1.setText(getString(R.string.temperature_output, forecast[i].getHighTemperature(), units.getTemperature()));
            lowTemperatureTextView1.setText(getString(R.string.temperature_output, forecast[i].getLowTemperature(), units.getTemperature()));



    }
}
