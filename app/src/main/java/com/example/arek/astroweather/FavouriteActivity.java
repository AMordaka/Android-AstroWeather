package com.example.arek.astroweather;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.arek.astroweather.adapter.SpinAdapter;
import com.example.arek.astroweather.data.Channel;
import com.example.arek.astroweather.data.FavouriteItem;
import com.example.arek.astroweather.listener.WeatherServiceListener;
import com.example.arek.astroweather.service.YahooWeatherService;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class FavouriteActivity extends AppCompatActivity implements WeatherServiceListener {

    private Spinner mySpinner;
    private Realm realm;
    private SpinAdapter adapter;
    private EditText editText;
    private List<FavouriteItem> listFavourites = new ArrayList<>();

    boolean choice;

    private YahooWeatherService weatherService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = Realm.getDefaultInstance();
        setContentView(R.layout.activity_favourite);
        editText = findViewById(R.id.localization_edit_text);
        mySpinner = (Spinner) findViewById(R.id.favorites_spinner);
        weatherService = new YahooWeatherService(this);
        refreshSpinAdapter();

    }

    void refreshDataFromDataBase(){
        RealmQuery query = realm.where(FavouriteItem.class);
        RealmResults results = query.findAll();
        listFavourites = new ArrayList<>();
        for(int i = 0; i < results.size(); i++){
            FavouriteItem item = (FavouriteItem) results.get(i);
            listFavourites.add(item);
        }
    }

    void refreshSpinAdapter(){
        refreshDataFromDataBase();
        adapter = new SpinAdapter(this,
                android.R.layout.simple_spinner_item,
                listFavourites);
        mySpinner.setAdapter(adapter);
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                FavouriteItem favouriteItem = adapter.getItem(position);
                setBooleanFromSharedPreferences(position);
                Toast.makeText(FavouriteActivity.this, favouriteItem.getLocation() + " " + favouriteItem.getWoeid() , Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapter) {  }
        });
    }


    void setBooleanFromSharedPreferences(int position){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("pref_is_favourite_location", "1");
        editor.putInt("pref_position", position);
        editor.apply();
    }

    public void saveLocation(View view) {
        Channel channel = weatherService.refreshWeather(editText.getText().toString());
        if(channel != null) {
            String link = channel.getItem().getLink();
            link = link.substring(link.lastIndexOf("-") + 1);
            String woeid = link.substring(0, link.length() - 1);
            final FavouriteItem favouriteItem = new FavouriteItem(editText.getText().toString(), woeid);

            if(listFavourites.contains(favouriteItem)){
                Toast.makeText(this, "This woeid is in Database", Toast.LENGTH_SHORT).show();
            }else {


                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("Alert");
                alertDialogBuilder
                        .setMessage("Are you sure add this localization: " + channel.getLocation())
                        .setCancelable(false)
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                realm.beginTransaction();
                                FavouriteItem itemInDb = realm.createObject(FavouriteItem.class);
                                itemInDb.setLocation(favouriteItem.getLocation());
                                itemInDb.setWoeid(favouriteItem.getWoeid());
                                realm.commitTransaction();
                                showToastAdded(favouriteItem);
                                refreshSpinAdapter();
                            }
                        })
                        .setNegativeButton("No",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                showToastCancel();
                                dialog.cancel();
                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        } else{
                Toast.makeText(this, "This location is Invalid", Toast.LENGTH_SHORT).show();
            }
    }

    void showToastAdded(FavouriteItem favouriteItem){
        Toast.makeText(this, "Added woeid: " + favouriteItem.getWoeid(), Toast.LENGTH_SHORT).show();
    }

    void showToastCancel(){
        Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void serviceSuccess(Channel channel) {

    }

    @Override
    public void serviceFailure(Exception exception) {

    }
}
