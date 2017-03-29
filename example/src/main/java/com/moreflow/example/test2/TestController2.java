package com.moreflow.example.test2;

import com.moreflow.core.controller.Controller;

public class TestController2 extends Controller<ITestView2> {

    @Override
    public void viewAttached() {
        this.view.setTestText("Hi from controller 2!");
    }

}
