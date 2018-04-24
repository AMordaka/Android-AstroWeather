package com.example.arek.astroweather;

public enum UpdateTimeIntervalValues {
    FIVE_SECONDS(5000),
    TEN_SECONDS(1000),
    THIRTY_SECONDS(30000),
    MINUTE(1000 * 60),
    FIFTEEN_MINUTES(1000 * 60 * 15);

    public final int seconds;

    UpdateTimeIntervalValues(int seconds) {
        this.seconds = seconds;
    }
}
