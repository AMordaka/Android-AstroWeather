package com.example.arek.astroweather.astroweather;

import com.astrocalculator.AstroCalculator;
import com.astrocalculator.AstroDateTime;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class AstroWeatherConfig {

    private AstroCalculator.Location location;
    private AstroCalculator astroCalculator;

    public AstroWeatherConfig() {
        this.location =  new AstroCalculator.Location(51.45, 19.28);
        this.astroCalculator =  new AstroCalculator(getAstroDateTime(), location);
    }

    private AstroDateTime getAstroDateTime() {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        return new AstroDateTime(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),  calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND), 1 , false);
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
}
