package com.example.arek.astroweather;

import android.net.ParseException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.astrocalculator.AstroCalculator;
import com.example.arek.astroweather.astroweather.AstroWeatherConfig;

import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    private static final String LATITUDE = "Latitude";
    private static final String LONGITUDE = "Longitude";
    private static final String INCORECT_DATA = "Incorect Data";
    private static final String SAVE_CHANGES = "Changes saved";

    private AstroWeatherConfig astroWeatherConfig;
    private Spinner refreshTimeSpinner;
    private EditText longitude;
    private EditText latitude;
    private Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        astroWeatherConfig = AstroWeatherConfig.getAstroWeatherInstance();
        initSpinner();
        initOthers();
    }

    List<String> getIntervalNames() {
        List<String> intervalNames = new ArrayList<>();
        for (UpdateTimeIntervalValues value : UpdateTimeIntervalValues.values()) {
            intervalNames.add(value.name());
        }
        return intervalNames;
    }

    void initOthers() {
        longitude = findViewById(R.id.newlongitude);
        latitude = findViewById(R.id.newlatitude);
        buttonSave = findViewById(R.id.buttonsave);
    }

    void initSpinner() {
        refreshTimeSpinner = (Spinner) findViewById(R.id.refresh_time);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, getIntervalNames());
        refreshTimeSpinner.setAdapter(adapter);
        refreshTimeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int id, long position) {

                switch ((int) position) {
                    case 0:
                        astroWeatherConfig.setTimeInterval(5000);
                        break;
                    case 1:
                        astroWeatherConfig.setTimeInterval(10000);
                        break;
                    case 2:
                        astroWeatherConfig.setTimeInterval(30000);
                        break;
                    case 3:
                        astroWeatherConfig.setTimeInterval(10 * 60);
                        break;
                    case 4:
                        astroWeatherConfig.setTimeInterval(15 * 1000 * 60);
                        break;
                }
                Toast.makeText(SettingsActivity.this, SAVE_CHANGES, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
    }

    public void saveHandler(View view) {
        try {
            double mlatitude = Double.parseDouble(latitude.getText().toString());
            double mlongitude = Double.parseDouble(longitude.getText().toString());
            if ((mlatitude > 90 || mlatitude < -90) || (mlongitude < 0 || mlongitude > 180)) {
                throw new Exception();
            }
            astroWeatherConfig.setLocation(new AstroCalculator.Location(mlatitude, mlongitude));
            Toast.makeText(SettingsActivity.this, SAVE_CHANGES, Toast.LENGTH_SHORT).show();
        } catch (Exception ParseException) {
            Toast.makeText(SettingsActivity.this, INCORECT_DATA, Toast.LENGTH_SHORT).show();
        }
    }
}
