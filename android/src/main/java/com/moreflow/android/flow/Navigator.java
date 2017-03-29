package com.moreflow.android.flow;

import android.app.Activity;
import android.content.Intent;

import com.moreflow.core.controller.Controller;
import com.moreflow.core.navigation.INavigator;
import com.moreflow.core.navigation.StateKey;

import java.io.Serializable;

import flow.Flow;

public class Navigator implements INavigator {

    private final Flow flow;
    private final Activity activity;
    private final Controller controller;

    private final Navigator parentNavigator;
    private final Navigator[] childNavigators;

    public Navigator(Flow flow,
                     Activity activity,
                     Controller controller,
                     Navigator parentNavigator,
                     Navigator[] childNavigators) {
        this.flow = flow;
        this.activity = activity;
        this.controller = controller;
        this.parentNavigator = parentNavigator;
        this.childNavigators = childNavigators;
    }

    @Override
    public void navigate(Class<? extends Controller> controllerClass) {
        if (parentNavigator != null) {
            parentNavigator.navigate(controllerClass);
            return;
        }
        this.flow.set(controllerClass);
    }

    @Override
    public void navigate(Class<? extends Controller> controllerClass, Object state) {
        if (parentNavigator != null) {
            parentNavigator.navigate(controllerClass, state);
            return;
        }
        StateKey stateKey = new StateKey(controllerClass, state);
        this.flow.set(stateKey);
    }

    @Override
    public void pushMessageUp(int messageId, Object value) {
        if (this.parentNavigator == null) return;
        pushMessage(parentNavigator, messageId, value);
    }

    @Override
    public void pushMessageUp(int messageId) {
        pushMessageUp(messageId, null);
    }

    @Override
    public void pushMessageDown(int messageId, Object value) {
        if (childNavigators == null) return;
        for (int i = 0; i < childNavigators.length; i++) {
            pushMessage(childNavigators[i], messageId, value);
        }
    }

    @Override
    public void pushMessageDown(int messageId) {
        pushMessageDown(messageId, null);
    }

    private void pushMessage(Navigator navigator, int messageId, Object value) {
        Controller controller = navigator.getController();
        controller.handleMessage(messageId, value);
    }

    public Controller getController() {
        return controller;
    }

    public Navigator[] getChildren() {
        return childNavigators;
    }

    @SuppressWarnings("CheckResult")
    @Override
    public void goBack() {
        this.flow.goBack();
    }

}
