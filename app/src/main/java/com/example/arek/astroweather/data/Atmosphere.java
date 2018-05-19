package com.example.arek.astroweather.data;

import org.json.JSONException;
import org.json.JSONObject;

public class Atmosphere implements JSONPopulator{

    private String humidity;
    private String pressure;
    private String rising;
    private String visibility;

    public String getHumidity() {
        return humidity;
    }

    public String getPressure() {
        return pressure;
    }

    public String getRising() {
        return rising;
    }

    public String getVisibility() {
        return visibility;
    }

    @Override
    public void populate(JSONObject data) {
        humidity = data.optString("humidity");
        pressure = data.optString("pressure");
        rising = data.optString("rising");
        visibility = data.optString("visibility");
    }

    @Override
    public JSONObject toJSON() {
        JSONObject data = new JSONObject();

        try {
            data.put("humidity", humidity);
            data.put("pressure", pressure);
            data.put("rising", rising);
            data.put("visibility", visibility);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return data;
    }
}
