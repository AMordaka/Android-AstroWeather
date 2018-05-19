package com.example.arek.astroweather.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.arek.astroweather.R;
import com.example.arek.astroweather.data.Channel;
import com.example.arek.astroweather.data.Condition;
import com.example.arek.astroweather.data.Units;
import com.example.arek.astroweather.data.Atmosphere;

public class BasicDataFragment extends Fragment {

    private ImageView weatherIconImageView;
    private TextView dayLabelTextView;
    private TextView highTemperatureTextView;
    private TextView lowTemperatureTextView;
    private TextView locationTextView;
    private TextView longitudeTextView;
    private TextView latitudeTextView;
    private TextView pressureTextView;
    private TextView descriptionTextView;




    public BasicDataFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basic, container, false);

        weatherIconImageView = (ImageView) view.findViewById(R.id.weatherIconImageView);
        dayLabelTextView = (TextView) view.findViewById(R.id.dayTextView);
        highTemperatureTextView = (TextView) view.findViewById(R.id.highTemperatureTextView);
        lowTemperatureTextView = (TextView) view.findViewById(R.id.lowTemperatureTextView);
        locationTextView = (TextView) view.findViewById(R.id.locationTextView);
        longitudeTextView = (TextView) view.findViewById(R.id.longitudeTextView);
        latitudeTextView = (TextView) view.findViewById(R.id.latitudeTextView);
        pressureTextView = (TextView) view.findViewById(R.id.pressureTextView);
        descriptionTextView = (TextView) view.findViewById(R.id.descriptionTextView);
        return view;
    }

    public void loadForecast(Channel channel) {

        Condition[] forecast = channel.getItem().getForecast();
        Units units = channel.getUnits();
        int weatherIconImageResource = getResources().getIdentifier("icon_" + forecast[0].getCode(), "drawable", getActivity().getPackageName());
        String temp = "Longitude: " + channel.getItem().getLongitude();
        longitudeTextView.setText(temp);
        temp = "Latitude: " + channel.getItem().getLatitude();
        latitudeTextView.setText(temp);
        temp = channel.getItem().getCondition().getDescription();
        descriptionTextView.setText(temp);
        temp = "Pressure: " + channel.getAtmosphere().getPressure() + " " +channel.getUnits().getPressure();
        pressureTextView.setText(temp);
        weatherIconImageView.setImageResource(weatherIconImageResource);
        dayLabelTextView.setText(forecast[0].getDay());
        highTemperatureTextView.setText(getString(R.string.temperature_output, forecast[0].getHighTemperature(), units.getTemperature()));
        lowTemperatureTextView.setText(getString(R.string.temperature_output, forecast[0].getLowTemperature(), units.getTemperature()));
        locationTextView.setText(channel.getLocation());


    }
}
