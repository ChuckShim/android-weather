package com.dev.chuck.weathergame;

import android.content.Context;

import java.util.List;

/**
 * Created by Chuck on 2015. 4. 21..
 */
public class FriendsManager {
    private static FriendsManager mInstance = null;
    private Context mCtx;

    private FriendsManager(Context ctx){
        this.mCtx = ctx;
    }

    public static FriendsManager getInstance(Context ctx){

        if(mInstance == null){
            mInstance = new FriendsManager(ctx.getApplicationContext());
        }
        return mInstance;
    }

    public void save(String name){
        Friends friend = new Friends(name);
        friend.save();
    }

    public List<Friends> selectAll(){
        List<Friends> friendsList = Friends.listAll(Friends.class);
        return  friendsList;
    }
}
