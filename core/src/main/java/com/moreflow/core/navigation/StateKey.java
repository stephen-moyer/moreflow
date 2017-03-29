package com.moreflow.core.navigation;

import com.moreflow.core.controller.Controller;

public class StateKey {

    private final Class<Controller> controller;
    private final Object state;

    public StateKey(Class<? extends Controller> controller, Object state) {
        this.controller = (Class<Controller>) controller;
        this.state = state;
    }

    public Class<Controller> getController() {
        return controller;
    }

    public Object getState() {
        return state;
    }

}
