package com.example.arek.astroweather;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    Spinner refreshTimeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        refreshTimeSpinner = (Spinner) findViewById(R.id.refresh_time);
        initSpinner();
    }

    List<String> getIntervalNames(){
        List<String> intervalNames = new ArrayList<>();
        for (UpdateTimeIntervalValues value : UpdateTimeIntervalValues.values()) {
            intervalNames.add(value.name());
        }
        return intervalNames;
    }

    void initSpinner(){
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, getIntervalNames());
        refreshTimeSpinner.setAdapter(adapter);
        refreshTimeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int id, long position) {

                Toast.makeText(SettingsActivity.this, "Wybrano opcję" + (id+1), Toast.LENGTH_SHORT).show();

                switch((int)position)
                {
                    case 0:
                        //wybrano pierwszy element
                        break;
                    case 1:
                        //wybrano drugi element
                        break;
                    case 2:
                        //wybrano trzeci element
                        break;
                    case 3:
                        //wybrano czwarty element
                        break;
                    case 4:
                        //wybrano piąty element
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
    }
}
