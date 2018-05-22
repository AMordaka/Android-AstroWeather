package com.example.arek.astroweather.data;

import java.io.Serializable;
import java.util.Objects;

import io.realm.RealmObject;

public class FavouriteItem extends RealmObject {

    private String woeid;
    private String location;

    public FavouriteItem(String location, String woeid) {
        this.woeid = woeid;
        this.location = location;
    }

    public FavouriteItem() {
    }

    public String getWoeid() {
        return woeid;
    }

    public String getLocation() {
        return location;
    }

    public void setWoeid(String woeid) {
        this.woeid = woeid;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        FavouriteItem that = (FavouriteItem) o;
        return Objects.equals(woeid, that.woeid);
    }

    @Override
    public int hashCode() {

        return Objects.hash(woeid, location);
    }

    @Override
    public String toString() {
        return location;
    }
}
