package com.dev.chuck.weathergame;

import com.orm.SugarRecord;

/**
 * Created by Chuck on 2015. 4. 17..
 */
public class Player extends SugarRecord<Player> {
    long timestamp;
    String name;
    double temperature;

    public Player(){
        super();
    }

    public Player(long timestamp, String name, double temperature){
        super();
        this.timestamp = timestamp;
        this.name = name;
        this.temperature = temperature;
    }

    public long getTimestamp(){
        return timestamp;
    }

    public void setTimestamp(long timestamp){
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
}
