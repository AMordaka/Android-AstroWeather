package com.example.arek.astroweather;

public enum UpdateTimeIntervalValues {
    FIVE_SECONDS(5000),
    TEN_SECONDS(1000),
    THIRTY_SECONDS(30000),
    MINUTE(60000),
    FIFTEEN_MINUTES(900000);

    public final int seconds;

    UpdateTimeIntervalValues(int seconds) {
        this.seconds = seconds;
    }
}
