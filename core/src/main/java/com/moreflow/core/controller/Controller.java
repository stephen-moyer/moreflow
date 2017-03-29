package com.moreflow.core.controller;

import com.moreflow.core.navigation.INavigator;
import com.moreflow.core.view.IView;

public abstract class Controller<T extends IView> {

    protected INavigator navigator;
    protected T view;

    public void viewAttached() {}
    public void viewDetached() {}

    public void handleMessage(int messageId, Object value) {}

    public void setNavigator(INavigator navigator) {
        this.navigator = navigator;
    }

    public void setView(T view) {
        if (this.view != null) throw new IllegalStateException("Cannot set the view when it's already been set.");
        this.view = view;
    }

    public T getView() {
        return view;
    }

    public void goBack() {
        this.navigator.goBack();
    }

}
