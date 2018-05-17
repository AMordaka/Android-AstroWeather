package com.example.arek.astroweather.listener;

import com.example.arek.astroweather.data.LocationResult;

public interface GeocodingServiceListener {
    void geocodeSuccess(LocationResult location);

    void geocodeFailure(Exception exception);
}
