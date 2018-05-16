/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Yoel Nunez <dev@nunez.guru>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 */
package com.example.arek.astroweather.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.arek.astroweather.MainActivity;
import com.example.arek.astroweather.R;
import com.example.arek.astroweather.astroweather.AstroWeatherConfig;


public class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener, SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String INCORECT_DATA = "Incorect Data";


    private SharedPreferences preferences;
    private AstroWeatherConfig astroWeatherConfig;

    private SwitchPreference geolocationEnabledPreference;
    private EditTextPreference manualLongitudePreference;
    private EditTextPreference manualLatitudePreference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        astroWeatherConfig = AstroWeatherConfig.getAstroWeatherInstance();
        findPreferencesFromXml();
        setupPreferences();
        checkPreferences();
    }


    void findPreferencesFromXml(){
        addPreferencesFromResource(R.xml.app_preferences);
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        geolocationEnabledPreference = (SwitchPreference) findPreference(getString(R.string.pref_geolocation_enabled));
        manualLongitudePreference = (EditTextPreference) findPreference(getString(R.string.pref_manual_longitude));
        manualLatitudePreference = (EditTextPreference) findPreference(getString(R.string.pref_manual_latitude));

    }

    void setupPreferences(){
        manualLatitudePreference.setText(String.valueOf(astroWeatherConfig.getLocation().getLatitude()));
        manualLongitudePreference.setText(String.valueOf(astroWeatherConfig.getLocation().getLongitude()));
        bindPreferenceSummaryToValue(manualLongitudePreference);
        bindPreferenceSummaryToValue(manualLatitudePreference);
        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_temperature_unit)));
        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_refreshing_time)));
        PreferenceManager.getDefaultSharedPreferences(getActivity()).registerOnSharedPreferenceChangeListener(this);
    }

    void checkPreferences(){
        if(!preferences.getBoolean(getString(R.string.pref_needs_setup), false)) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(getString(R.string.pref_needs_setup), false);
            editor.apply();
        }
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            startActivity(new Intent(getActivity(), MainActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        if (geolocationEnabledPreference.isChecked()) {
            manualLongitudePreference.setEnabled(false);
            manualLatitudePreference.setEnabled(false);
        } else {
            manualLongitudePreference.setEnabled(true);
            manualLatitudePreference.setEnabled(true);
        }

    }

    private void bindPreferenceSummaryToValue(Preference preference) {
        preference.setOnPreferenceChangeListener(this);
        onPreferenceChange(preference, preferences.getString(preference.getKey(), null));
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object value) {
        String stringValue = value.toString();

        if (preference instanceof ListPreference) {
            ListPreference listPreference = (ListPreference) preference;
            int index = listPreference.findIndexOfValue(stringValue);

            preference.setSummary(index >= 0 ? listPreference.getEntries()[index] : null);

        } else if (preference instanceof EditTextPreference) {

            try {
                double doubleValue = Double.parseDouble(stringValue);
                if (doubleValue < 0 || doubleValue > 180) {
                    throw new Exception();
                }
            }catch (Exception e) {
                    Toast.makeText(getActivity(), INCORECT_DATA, Toast.LENGTH_SHORT).show();
                    return false;
                }
            preference.setSummary(stringValue);
        }

        return true;
    }
}
