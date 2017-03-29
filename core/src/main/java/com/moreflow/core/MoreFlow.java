package com.moreflow.core;

import com.moreflow.core.platform.IPlatform;
import com.moreflow.core.platform.IPreferences;

public class MoreFlow {

    private final IPreferences preferences;
    private final IPlatform platform;

    public MoreFlow(IPreferences preferences, IPlatform platform) {
        this.preferences = preferences;
        this.platform = platform;
    }
}
