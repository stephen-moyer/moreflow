package com.moreflow.android;

import android.app.Application;

import com.moreflow.android.platform.AndroidPlatform;
import com.moreflow.android.platform.AndroidPreferences;
import com.moreflow.core.MoreFlow;

public class MoreFlowApplication extends Application {

    private MoreFlow moreFlow;

    @Override
    public void onCreate() {
        super.onCreate();
        this.moreFlow = new MoreFlow(new AndroidPreferences(this), new AndroidPlatform(this));
    }

    public MoreFlow getMoreFlow() {
        return moreFlow;
    }

}
