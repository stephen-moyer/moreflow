package com.moreflow.android.view;

import android.support.annotation.LayoutRes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ViewMetadata {

    @LayoutRes int layoutId() default -1;
    ViewChildMetadata[] children() default {};

}