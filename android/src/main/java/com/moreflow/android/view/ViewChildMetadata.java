package com.moreflow.android.view;

import android.support.annotation.IdRes;

import com.moreflow.core.controller.Controller;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ViewChildMetadata {

    @IdRes int viewId();
    Class<Controller> controller();

}
