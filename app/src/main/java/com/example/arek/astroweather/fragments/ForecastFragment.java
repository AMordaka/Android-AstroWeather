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


public class ForecastFragment extends Fragment {

    private ImageView weatherIconImageView;
    private TextView dayLabelTextView;
    private TextView highTemperatureTextView;
    private TextView lowTemperatureTextView;

    private ImageView weatherIconImageView1;
    private TextView dayLabelTextView1;
    private TextView highTemperatureTextView1;
    private TextView lowTemperatureTextView1;

    private ImageView weatherIconImageView2;
    private TextView dayLabelTextView2;
    private TextView highTemperatureTextView2;
    private TextView lowTemperatureTextView2;

    private ImageView weatherIconImageView3;
    private TextView dayLabelTextView3;
    private TextView highTemperatureTextView3;
    private TextView lowTemperatureTextView3;

    private ImageView weatherIconImageView4;
    private TextView dayLabelTextView4;
    private TextView highTemperatureTextView4;
    private TextView lowTemperatureTextView4;

    private ImageView weatherIconImageView5;
    private TextView dayLabelTextView5;
    private TextView highTemperatureTextView5;
    private TextView lowTemperatureTextView5;

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

        weatherIconImageView2 = (ImageView) view.findViewById(R.id.weatherIconImageView2);
        dayLabelTextView2 = (TextView) view.findViewById(R.id.dayTextView2);
        highTemperatureTextView2 = (TextView) view.findViewById(R.id.highTemperatureTextView2);
        lowTemperatureTextView2 = (TextView) view.findViewById(R.id.lowTemperatureTextView2);

        weatherIconImageView3 = (ImageView) view.findViewById(R.id.weatherIconImageView3);
        dayLabelTextView3 = (TextView) view.findViewById(R.id.dayTextView3);
        highTemperatureTextView3 = (TextView) view.findViewById(R.id.highTemperatureTextView3);
        lowTemperatureTextView3 = (TextView) view.findViewById(R.id.lowTemperatureTextView3);

        weatherIconImageView4 = (ImageView) view.findViewById(R.id.weatherIconImageView4);
        dayLabelTextView4 = (TextView) view.findViewById(R.id.dayTextView4);
        highTemperatureTextView4 = (TextView) view.findViewById(R.id.highTemperatureTextView4);
        lowTemperatureTextView4 = (TextView) view.findViewById(R.id.lowTemperatureTextView4);

        weatherIconImageView5 = (ImageView) view.findViewById(R.id.weatherIconImageView5);
        dayLabelTextView5 = (TextView) view.findViewById(R.id.dayTextView5);
        highTemperatureTextView5 = (TextView) view.findViewById(R.id.highTemperatureTextView5);
        lowTemperatureTextView5 = (TextView) view.findViewById(R.id.lowTemperatureTextView5);

        return view;
    }

    public void loadForecast(Channel channel) {
        Condition[] forecast = channel.getItem().getForecast();
        Units units = channel.getUnits();

        int weatherIconImageResource = getResources().getIdentifier("icon_" + forecast[0].getCode(), "drawable", getActivity().getPackageName());

        weatherIconImageView.setImageResource(weatherIconImageResource);
        dayLabelTextView.setText(forecast[0].getDay());
        highTemperatureTextView.setText(getString(R.string.temperature_output, forecast[0].getHighTemperature(), units.getTemperature()));
        lowTemperatureTextView.setText(getString(R.string.temperature_output, forecast[0].getLowTemperature(), units.getTemperature()));

        weatherIconImageResource = getResources().getIdentifier("icon_" + forecast[1].getCode(), "drawable", getActivity().getPackageName());

        weatherIconImageView1.setImageResource(weatherIconImageResource);
        dayLabelTextView1.setText(forecast[1].getDay());
        highTemperatureTextView1.setText(getString(R.string.temperature_output, forecast[1].getHighTemperature(), units.getTemperature()));
        lowTemperatureTextView1.setText(getString(R.string.temperature_output, forecast[1].getLowTemperature(), units.getTemperature()));

        weatherIconImageResource = getResources().getIdentifier("icon_" + forecast[2].getCode(), "drawable", getActivity().getPackageName());

        weatherIconImageView2.setImageResource(weatherIconImageResource);
        dayLabelTextView2.setText(forecast[2].getDay());
        highTemperatureTextView2.setText(getString(R.string.temperature_output, forecast[2].getHighTemperature(), units.getTemperature()));
        lowTemperatureTextView2.setText(getString(R.string.temperature_output, forecast[2].getLowTemperature(), units.getTemperature()));

        weatherIconImageResource = getResources().getIdentifier("icon_" + forecast[3].getCode(), "drawable", getActivity().getPackageName());

        weatherIconImageView3.setImageResource(weatherIconImageResource);
        dayLabelTextView3.setText(forecast[3].getDay());
        highTemperatureTextView3.setText(getString(R.string.temperature_output, forecast[3].getHighTemperature(), units.getTemperature()));
        lowTemperatureTextView3.setText(getString(R.string.temperature_output, forecast[3].getLowTemperature(), units.getTemperature()));

        weatherIconImageResource = getResources().getIdentifier("icon_" + forecast[4].getCode(), "drawable", getActivity().getPackageName());

        weatherIconImageView4.setImageResource(weatherIconImageResource);
        dayLabelTextView4.setText(forecast[4].getDay());
        highTemperatureTextView4.setText(getString(R.string.temperature_output, forecast[4].getHighTemperature(), units.getTemperature()));
        lowTemperatureTextView4.setText(getString(R.string.temperature_output, forecast[4].getLowTemperature(), units.getTemperature()));

        weatherIconImageResource = getResources().getIdentifier("icon_" + forecast[5].getCode(), "drawable", getActivity().getPackageName());

        weatherIconImageView5.setImageResource(weatherIconImageResource);
        dayLabelTextView5.setText(forecast[5].getDay());
        highTemperatureTextView5.setText(getString(R.string.temperature_output, forecast[5].getHighTemperature(), units.getTemperature()));
        lowTemperatureTextView5.setText(getString(R.string.temperature_output, forecast[5].getLowTemperature(), units.getTemperature()));
    }
}
