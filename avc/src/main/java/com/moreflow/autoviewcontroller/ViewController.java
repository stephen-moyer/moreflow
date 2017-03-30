package com.moreflow.autoviewcontroller;

import com.moreflow.core.controller.Controller;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public @interface ViewController {

    Class<? extends Controller> controller();

}
