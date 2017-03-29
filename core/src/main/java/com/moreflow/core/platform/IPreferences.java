package com.moreflow.core.platform;

public interface IPreferences {

    boolean getBoolean(String key, boolean defaultValue);
    void setBoolean(String key, boolean value);
    double getDouble(String key, double defaultValue);
    void setDouble(String key, double value);
    float getFloat(String key, float defaultValue);
    void setFloat(String key, float value);

}
