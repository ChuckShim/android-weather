package com.dev.chuck.weathergame;

import android.content.Context;

import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;

/**
 * Created by Chuck on 2015. 4. 17..
 */
public class UserManager {
    private static UserManager mInstance = null;
    private Context mCtx;

    private UserManager(Context ctx){
        this.mCtx = ctx;
    }

    public static UserManager getInstance(Context ctx){

        if(mInstance == null){
            mInstance = new UserManager(ctx.getApplicationContext());
        }
        return mInstance;
    }

    public void save(long timestamp, String name, double temperature){
        Player player = new Player(timestamp, name, temperature);
        player.save();
    }

    public List<Player> selectList(long timestamp){

        Player player = null;

        List<Player> playerList = Select.from(Player.class)
                .where(Condition.prop("timestamp").eq(timestamp))
                .list();

        return playerList;
    }
}
