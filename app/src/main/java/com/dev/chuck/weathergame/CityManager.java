package com.dev.chuck.weathergame;

import android.content.Context;

import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;
import java.util.Map;

/**
 * Created by Chuck on 2015. 4. 14..
 */
public class CityManager {

    private static CityManager mInstance = null;
    private Context mCtx;

    private CityManager(Context ctx){
        this.mCtx = ctx;
    }

    public static CityManager getInstance(Context ctx){

        if(mInstance == null){
            mInstance = new CityManager(ctx.getApplicationContext());
        }
        return mInstance;
    }


    public void save(String name, String country, double latitude, double longitude){
        City city = new City(name, country, latitude, longitude);
        city.save();

    }

    public List<City> selectAll(){
        List<City> cityList = City.listAll(City.class);
        return  cityList;
    }

    public City select(String name, String country){

        City city = null;

        List<City> cityList = Select.from(City.class)
                .where(Condition.prop("name").eq(name),
                        Condition.prop("country").eq(country))
                .limit("1").list();

        if(cityList != null
                && cityList.size() > 0){
            city = cityList.get(0);
        }
        return city;
    }

    public void deleteAll(){
        City.deleteAll(City.class);
    }
}
