package com.example.arek.astroweather.astroweather;

import android.os.Handler;
import android.widget.Toast;

import com.astrocalculator.AstroCalculator;
import com.astrocalculator.AstroDateTime;
import com.example.arek.astroweather.MainActivity;
import com.example.arek.astroweather.SettingsActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;

public class AstroWeatherConfig {

    private static final AstroWeatherConfig astroWeatherInstance = new AstroWeatherConfig();

    private AstroCalculator.Location location;
    private AstroCalculator astroCalculator;
    private Runnable updateAstro;
    private Set<AstroCallback> subscribers = new HashSet<>();
    private long timeInterval = 10000;
    final Handler handler = new Handler();


    public AstroWeatherConfig() {
        this.location = new AstroCalculator.Location(51.45, 19.28);
        this.astroCalculator = new AstroCalculator(getAstroDateTime(), location);
        updateAstro = new Runnable() {
            @Override
            public void run() {
                update();
                notifySubscribers();
                handler.postDelayed(this, timeInterval);
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
        long deviceDate = System.currentTimeMillis();
        int year = Integer.parseInt(new SimpleDateFormat("YYYY", Locale.ENGLISH).format(deviceDate));
        int month = Integer.parseInt(new SimpleDateFormat("MM", Locale.ENGLISH).format(deviceDate));
        int day = Integer.parseInt(new SimpleDateFormat("dd", Locale.ENGLISH).format(deviceDate));
        int hour = Integer.parseInt(new SimpleDateFormat("hh", Locale.GERMANY).format(deviceDate));
        int minute = Integer.parseInt(new SimpleDateFormat("mm", Locale.GERMANY).format(deviceDate));
        int second = Integer.parseInt(new SimpleDateFormat("ss", Locale.GERMANY).format(deviceDate));
        int timeZoneOffset = 1;
        boolean dayLightSaving = true;
        return new AstroDateTime(year, month, day, hour, minute, second, timeZoneOffset, dayLightSaving);
    }

    public AstroCalculator.Location getLocation() {
        return location;
    }

    public void setLocation(AstroCalculator.Location location) {
        this.location = location;
        update();
        notifySubscribers();
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

    void notifySubscribers() {
        for (AstroCallback subscriber : subscribers) {
            subscriber.onSettingsUpdate();
        }
    }

    public void setTimeInterval(long timeInterval) {
        this.timeInterval = timeInterval;
        update();
        notifySubscribers();
    }

    public long getTimeInterval(){
        return this.timeInterval;
}

}
