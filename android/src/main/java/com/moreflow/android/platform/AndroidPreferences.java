package com.moreflow.android.platform;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.moreflow.android.MoreFlowApplication;
import com.moreflow.core.platform.IPreferences;

public class AndroidPreferences implements IPreferences {

    private static final String SHARED_PREFS_NAME = "com.cueme.android";

    private final Application application;
    private final SharedPreferences sharedPreferences;

    public AndroidPreferences(Application application) {
        this.application = application;
        this.sharedPreferences = application.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public boolean getBoolean(String key, boolean defaultValue) {
        return this.sharedPreferences.getBoolean(key, defaultValue);
    }

    @Override
    public void setBoolean(String key, boolean value) {
        this.sharedPreferences.edit().putBoolean(key, value).apply();
    }

    @Override
    public double getDouble(String key, double defaultValue) {
        if (!this.sharedPreferences.contains(key)) return defaultValue;
        long longVal = this.sharedPreferences.getLong(key, 0);
        return Double.longBitsToDouble(longVal);
    }

    @Override
    public void setDouble(String key, double value) {
        this.sharedPreferences.edit().putLong(key, Double.doubleToLongBits(value)).apply();
    }

    @Override
    public float getFloat(String key, float defaultValue) {
        if (!this.sharedPreferences.contains(key)) return defaultValue;
        return this.sharedPreferences.getFloat(key, 0);
    }

    @Override
    public void setFloat(String key, float value) {
        this.sharedPreferences.edit().putFloat(key, value).apply();
    }

}
