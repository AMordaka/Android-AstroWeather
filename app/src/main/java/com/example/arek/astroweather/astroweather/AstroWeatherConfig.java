package com.example.arek.astroweather.astroweather;

import android.os.Handler;

import com.astrocalculator.AstroCalculator;
import com.astrocalculator.AstroDateTime;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TimeZone;

public class AstroWeatherConfig {

    private AstroCalculator.Location location;
    private AstroCalculator astroCalculator;
    private static final AstroWeatherConfig astroWeatherInstance = new AstroWeatherConfig();
    private Runnable updateAstro;
    private Set<AstroCallback> subscribers = new HashSet<>();
    final Handler handler = new Handler();

    public AstroWeatherConfig() {
        this.location = new AstroCalculator.Location(51.45, 19.28);
        this.astroCalculator = new AstroCalculator(getAstroDateTime(), location);
        updateAstro = new Runnable() {
            @Override
            public void run() {
                update();
                notifySubscribers();
                handler.postDelayed(this, 5000);
            }
        };
        handler.post(updateAstro);
    }

    public static AstroWeatherConfig getAstroWeatherInstance() {
        return astroWeatherInstance;
    }

    public void update() {
        astroCalculator = new AstroCalculator(getAstroDateTime(), location);
    }

    private AstroDateTime getAstroDateTime() {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        return new AstroDateTime(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND), 1, false);
    }

    public AstroCalculator.Location getLocation() {
        return location;
    }

    public void setLocation(AstroCalculator.Location location) {
        this.location = location;
    }

    public AstroCalculator getAstroCalculator() {
        return astroCalculator;
    }

    public void setAstroCalculator(AstroCalculator astroCalculator) {
        this.astroCalculator = astroCalculator;
    }

    public void registerForUpdates(AstroCallback subscriber) {
        subscribers.add(subscriber);
    }

    public void unregisterForUpdates(AstroCallback subscriber) {
        subscribers.remove(subscriber);
    }

    void notifySubscribers(){
        for (AstroCallback subscriber : subscribers) {
            subscriber.onSettingsUpdate();
        }
    }
}
