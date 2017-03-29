package com.moreflow.android.view;

import android.view.View;

import com.moreflow.core.controller.Controller;

public interface IAndroidView<T extends Controller> {

    void viewLoaded(View view, T controller);
    void viewDestroyed();

}
