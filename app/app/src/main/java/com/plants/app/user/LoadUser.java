package com.plants.app.user;

import android.content.Context;

import com.plants.app.adapters.JSONHelper;

public class LoadUser {

    public static User getUser(Context context){
        User user;
        if (JSONHelper.importJsonUser(context) == null){
            user = new User("username");
            JSONHelper.saveJsonUser(context, user);
        }
        else{
            user = JSONHelper.importJsonUser(context);
        }
        return user;
    }
}
