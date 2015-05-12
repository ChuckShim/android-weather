package com.dev.chuck.weathergame;

import android.content.Context;

import com.orm.SugarRecord;

/**
 * Created by Chuck on 2015. 4. 10..
 */
public class City extends SugarRecord<City>{

    String name;
    String country;
    double latitude;
    double longitude;

    public City(){
        super();
    }

    public City(String name, String country, double latitude, double longitude){
        super();
        this.name = name;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

}
