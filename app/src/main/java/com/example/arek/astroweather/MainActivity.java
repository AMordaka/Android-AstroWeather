package com.example.arek.astroweather;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.astrocalculator.AstroCalculator;
import com.example.arek.astroweather.adapter.PagerAdapter;
import com.example.arek.astroweather.astroweather.AstroWeatherConfig;
import com.example.arek.astroweather.data.Channel;
import com.example.arek.astroweather.data.FavouriteItem;
import com.example.arek.astroweather.fragments.BasicDataFragment;
import com.example.arek.astroweather.fragments.ForecastFragment;
import com.example.arek.astroweather.listener.WeatherServiceListener;
import com.example.arek.astroweather.service.WeatherCacheService;
import com.example.arek.astroweather.service.YahooWeatherService;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmModel;
import io.realm.RealmQuery;
import io.realm.RealmResults;


public class MainActivity extends AppCompatActivity implements WeatherServiceListener, LocationListener {

    private final static String SUN = "Sun";
    private final static String MOON = "Moon";
    private final static String WEATHER = "Weather";
    public static int GET_WEATHER_FROM_CURRENT_LOCATION = 0x00001;

    private ViewPager viewPager;
    private TabLayout tabLayout;

    private Realm realm;

    private boolean weatherServicesHasFailed = false;

    private ProgressDialog loadingDialog;
    private SharedPreferences preferences;

    private AstroWeatherConfig astroWeatherConfig;
    private WeatherCacheService cacheService;
    private YahooWeatherService weatherService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.edit().remove(getString(R.string.pref_manual_latitude)).apply();
        preferences.edit().remove(getString(R.string.pref_manual_longitude)).apply();
        preferences.edit().remove(getString(R.string.pref_is_favourite_location)).apply();

        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();

        astroWeatherConfig = AstroWeatherConfig.getAstroWeatherInstance();
        super.onCreate(savedInstanceState);
        initDataBase();
        weatherService = new YahooWeatherService(this);
        cacheService = new WeatherCacheService(this);

        setContentView(R.layout.activity_main);
        if (getResources().getBoolean(R.bool.isTablet)) {
            configureToolbar();
            configureTabLayout();
            configureViewPager();
        } else {
            configureToolbar();
        }
    }


    private void initDataBase(){
        Realm.init(this);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .name("location.db")
                .schemaVersion(0)
                .build();
        Realm.setDefaultConfiguration(realmConfig);

        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.deleteAll();
        FavouriteItem favouriteItem = realm.createObject(FavouriteItem.class);
        favouriteItem.setLocation("Lodz");
        favouriteItem.setWoeid("505120");
        realm.commitTransaction();


    }

    @Override
    protected void onStart() {
        super.onStart();
        weatherService.setTemperatureUnit(preferences.getString(getString(R.string.pref_temperature_unit), "C"));
        synchAstroWeather();

        loadingDialog = new ProgressDialog(this);
        loadingDialog.setMessage(getString(R.string.loading));
        loadingDialog.setCancelable(false);
        loadingDialog.show();

        String location = "Lodz";
        if(getBooleanFromSharedPreferences()) {
            location = preferences.getString(getString(R.string.pref_custom), "Lodz");
        }else{
            RealmQuery query = realm.where(FavouriteItem.class);
            RealmResults results = query.findAll();
            FavouriteItem item = (FavouriteItem) results.get(getPostion());
            location = item.getLocation();
        }
        if (location != null) {
            weatherService.refreshWeather(location);
        }
    }

    boolean getBooleanFromSharedPreferences(){
        String check = preferences.getString("pref_is_favourite_location","0");
        if(check.equals("0")){
            return true;
        }
        return false;
    }

    int getPostion(){
        return preferences.getInt("pref_position",0);
    }

    private void getWeatherFromCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
            }, GET_WEATHER_FROM_CURRENT_LOCATION);

            return;
        }

        // system's LocationManager
        final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        Criteria locationCriteria = new Criteria();

        if (isNetworkEnabled) {
            locationCriteria.setAccuracy(Criteria.ACCURACY_COARSE);
        } else if (isGPSEnabled) {
            locationCriteria.setAccuracy(Criteria.ACCURACY_FINE);
        }

        locationManager.requestSingleUpdate(locationCriteria, this, null);
    }

/*    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == MainActivity.GET_WEATHER_FROM_CURRENT_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getWeatherFromCurrentLocation();
            } else {
                loadingDialog.hide();

                AlertDialog messageDialog = new AlertDialog.Builder(this)
                        .setMessage(getString(R.string.location_permission_needed))
                        .setPositiveButton(getString(R.string.disable_geolocation), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startSettingsActivity();
                            }
                        })
                        .create();

                messageDialog.show();
            }
        }
    }*/

    private void startSettingsActivity() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    void synchAstroWeather() {
        astroWeatherConfig.setTimeInterval(preferences.getString(getString(R.string.pref_refreshing_time), "5000"));
        double mlatitude = Double.parseDouble(preferences.getString(getString(R.string.pref_manual_latitude), "52"));
        double mlongitude = Double.parseDouble(preferences.getString(getString(R.string.pref_manual_longitude), "22"));
        astroWeatherConfig.setLocation(new AstroCalculator.Location(mlatitude, mlongitude));
    }

    void configureTabLayout() {
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(WEATHER));
        tabLayout.addTab(tabLayout.newTab().setText(MOON));
        tabLayout.addTab(tabLayout.newTab().setText(SUN));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    void configureToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        setSupportActionBar(toolbar);
    }

    void configureViewPager() {
        viewPager = (ViewPager) findViewById(R.id.pager);
        if (viewPager != null) {
            viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
            viewPager.setOffscreenPageLimit(4);
            tabLayout.setupWithViewPager(viewPager);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            case R.id.action_fav:
                startActivity(new Intent(this, FavouriteActivity.class));
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public void serviceSuccess(Channel channel) {
        loadingDialog.dismiss();

        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();

        for (Fragment fragment : fragmentList) {
            if (fragment instanceof ForecastFragment) {
                ((ForecastFragment) fragment).loadForecast(channel);
            }
            if (fragment instanceof BasicDataFragment) {
                ((BasicDataFragment) fragment).loadForecast(channel);
            }
        }
        cacheService.save(channel);
    }

    @Override
    public void serviceFailure(Exception exception) {
        // display error if this is the second failure
        if (weatherServicesHasFailed) {
            loadingDialog.dismiss();
            Toast.makeText(this, exception.getMessage(), Toast.LENGTH_LONG).show();
        } else {
            // error doing reverse geocoding, load weather data from cache
            weatherServicesHasFailed = true;
            // OPTIONAL: let the user know an error has occurred then fallback to the cached data
            Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();

            cacheService.load(this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


}
