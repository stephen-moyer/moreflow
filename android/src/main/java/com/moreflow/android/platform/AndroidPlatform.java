package com.moreflow.android.platform;

import android.app.Application;

import com.moreflow.android.MoreFlowApplication;
import com.moreflow.core.platform.IPlatform;

public class AndroidPlatform implements IPlatform {

    private final Application application;

    public AndroidPlatform(Application application) {
        this.application = application;
    }

    @Override
    public String cacheDir() {
        return application.getCacheDir().getAbsolutePath();
    }

}
