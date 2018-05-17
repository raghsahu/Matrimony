package com.inspire.rkspmatrimony.sharedprefrence;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.inspire.rkspmatrimony.Models.LoginDTO;
import com.inspire.rkspmatrimony.Models.UserDTO;

import java.lang.reflect.Type;

public class SharedPrefrence {
    public static SharedPreferences myPrefs;
    public static SharedPreferences.Editor prefsEditor;
    public static SharedPrefrence myObj;

    private SharedPrefrence() {
    }

    public static SharedPrefrence getInstance(Context ctx) {
        if (myObj == null) {
            myObj = new SharedPrefrence();
            myPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
            prefsEditor = myPrefs.edit();
        }
        return myObj;
    }

    public void clearPreferences(String key) {
        prefsEditor.remove(key);
        prefsEditor.commit();
    }

    public void clearAllPreference() {
        prefsEditor = myPrefs.edit();
        prefsEditor.clear();
        prefsEditor.commit();
    }

    public long getLongValue(String Tag) {
        return myPrefs.getLong(Tag, 0L);

    }

    public void setLongValue(String Tag, long token) {
        prefsEditor.putLong(Tag, token);
        prefsEditor.commit();
    }

    public boolean getBooleanValue(String Tag) {
        return myPrefs.getBoolean(Tag, false);

    }

    public void setBooleanValue(String Tag, boolean token) {
        prefsEditor.putBoolean(Tag, token);
        prefsEditor.commit();
    }


    public int getIntValue(String Tag) {
        return myPrefs.getInt(Tag, 0);

    }

    public void setIntValue(String Tag, int value) {
        prefsEditor.putInt(Tag, value);
        prefsEditor.commit();
    }


    public String getValue(String Tag) {
        return myPrefs.getString(Tag, "default");
    }

    public void setValue(String Tag, String token) {
        prefsEditor.putString(Tag, token);
        prefsEditor.commit();
    }


    public void setLoginResponse(LoginDTO loginDTO, String tag) {

        //convert to string using gson
        Gson gson = new Gson();
        String hashMapString = gson.toJson(loginDTO);

        prefsEditor.putString(tag, hashMapString);
        prefsEditor.apply();
    }

    public LoginDTO getLoginResponse(String tag) {
        String obj = myPrefs.getString(tag, "defValue");
        if (obj.equals("defValue")) {
            return new LoginDTO();
        } else {

            Gson gson = new Gson();
            String storedHashMapString = myPrefs.getString(tag, "");
            Type type = new TypeToken<LoginDTO>() {
            }.getType();
            LoginDTO testHashMap = gson.fromJson(storedHashMapString, type);

            return testHashMap;
        }
    }

    public void setUserResponse(UserDTO userDTO, String tag) {

        //convert to string using gson
        Gson gson = new Gson();
        String hashMapString = gson.toJson(userDTO);

        prefsEditor.putString(tag, hashMapString);
        prefsEditor.apply();
    }

    public UserDTO getUserResponse(String tag) {
        String obj = myPrefs.getString(tag, "defValue");
        if (obj.equals("defValue")) {
            return new UserDTO();
        } else {

            Gson gson = new Gson();
            String storedHashMapString = myPrefs.getString(tag, "");
            Type type = new TypeToken<UserDTO>() {
            }.getType();
            UserDTO testHashMap = gson.fromJson(storedHashMapString, type);

            return testHashMap;
        }
    }


}