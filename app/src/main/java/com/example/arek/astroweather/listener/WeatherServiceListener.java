package com.example.arek.astroweather.listener;


import com.example.arek.astroweather.data.Channel;

public interface WeatherServiceListener {
    void serviceSuccess(Channel channel);

    void serviceFailure(Exception exception);
}
