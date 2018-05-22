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
import com.example.arek.astroweather.fragments.Preference.LatitudeEditTextPreference;
import com.example.arek.astroweather.fragments.Preference.LocationEditTextPreference;
import com.example.arek.astroweather.fragments.Preference.LongitudeEditTextPreference;


public class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener, SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String INCORECT_DATA = "Incorect Data";


    private SharedPreferences preferences;
    private AstroWeatherConfig astroWeatherConfig;

    private LongitudeEditTextPreference manualLongitudePreference;
    private LatitudeEditTextPreference manualLatitudePreference;

    private ListPreference refreshListPreference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        astroWeatherConfig = AstroWeatherConfig.getAstroWeatherInstance();
        findPreferencesFromXml();
        setupPreferences();
        checkPreferences();
    }


    void findPreferencesFromXml() {
        addPreferencesFromResource(R.xml.app_preferences);
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        manualLongitudePreference = (LongitudeEditTextPreference) findPreference(getString(R.string.pref_manual_longitude));
        manualLatitudePreference = (LatitudeEditTextPreference) findPreference(getString(R.string.pref_manual_latitude));
        refreshListPreference = (ListPreference) findPreference(getString(R.string.pref_refreshing_time));
    }

    void setupPreferences() {
        refreshListPreference.setSummary("15min");
        manualLatitudePreference.setText(String.valueOf(astroWeatherConfig.getLocation().getLatitude()));
        manualLongitudePreference.setText(String.valueOf(astroWeatherConfig.getLocation().getLongitude()));
        bindPreferenceSummaryToValue(refreshListPreference);
        bindPreferenceSummaryToValue(manualLongitudePreference);
        bindPreferenceSummaryToValue(manualLatitudePreference);
        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_temperature_unit)));
        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_refreshing_time)));
        PreferenceManager.getDefaultSharedPreferences(getActivity()).registerOnSharedPreferenceChangeListener(this);
    }

    void checkPreferences() {
        if (!preferences.getBoolean(getString(R.string.pref_needs_setup), false)) {
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
            manualLongitudePreference.setEnabled(true);
            manualLatitudePreference.setEnabled(true);
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

        } else if (preference instanceof LatitudeEditTextPreference) {
            try {
                double doubleValue = Double.parseDouble(stringValue);
                if (doubleValue < -90 || doubleValue > 90) {
                    throw new Exception();
                }
            } catch (Exception e) {
                Toast.makeText(getActivity(), INCORECT_DATA, Toast.LENGTH_SHORT).show();
                return false;
            }
            preference.setSummary(stringValue);
        }
        else if (preference instanceof LongitudeEditTextPreference) {
            try {
                double doubleValue = Double.parseDouble(stringValue);
                if (doubleValue < -180 || doubleValue > 180) {
                    throw new Exception();
                }
            } catch (Exception e) {
                Toast.makeText(getActivity(), INCORECT_DATA, Toast.LENGTH_SHORT).show();
                return false;
            }
            preference.setSummary(stringValue);
        }
        else if(preference instanceof LocationEditTextPreference){
            preference.setSummary(stringValue);
            setBooleanFromSharedPreferences();
        }

        return true;
    }

    void setBooleanFromSharedPreferences(){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("pref_is_favourite_location", "0");
        editor.apply();
    }
}
