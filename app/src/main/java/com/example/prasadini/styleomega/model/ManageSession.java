package com.example.prasadini.styleomega.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Prasadini on 23/8/2017.
 */

public class ManageSession {
    private SharedPreferences pref;

    public ManageSession(Context context){
        pref = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setUsername(String username){
        pref.edit().putString("username", username).commit();
    }

    public String getUsername(){
        String username = pref.getString("username","");
        return username;
    }

    public void logout(){
        pref.edit().remove("username").commit();
        pref.edit().clear();
    }
}
