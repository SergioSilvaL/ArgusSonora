package com.tecnologiasintech.argussonora.data.local;

import android.content.Context;
import android.content.SharedPreferences;

public class UserPrefs implements IUserPrefs {
    private String EMAIL_TAG = "Email";
    private String ZONE_TAG = "Zone";
    private String PREFS_NAME = "com.tecnologiasintech.argussonora.settings";
    private SharedPreferences settings;

    public UserPrefs(Context context) {
        settings = context.getSharedPreferences(PREFS_NAME, 0);
    }

    @Override
    public String getSupervisorEmail() {
        return settings.getString(EMAIL_TAG, "");
    }

    @Override
    public void setSupervisorEmail(String email) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(EMAIL_TAG, email);
        editor.apply();
    }

    @Override
    public String getSupervisorZone() {
        return settings.getString(ZONE_TAG, "");
    }

    @Override
    public void setSupervisorZone(String zone) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(ZONE_TAG, zone);
        editor.apply();
    }
}
