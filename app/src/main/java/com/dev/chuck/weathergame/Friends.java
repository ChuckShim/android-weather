package com.dev.chuck.weathergame;

import com.orm.SugarRecord;

/**
 * Created by Chuck on 2015. 4. 21..
 */
public class Friends extends SugarRecord<Friends> {

    String name;

    public Friends(){super();};

    public Friends(String name){
        super();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
