package com.moreflow.example.test;

import com.moreflow.core.controller.Controller;
import com.moreflow.example.test2.TestController2;

public class TestController extends Controller<ITestView> {

    @Override
    public void viewAttached() {
        view.setTestText("Hi from controller");
    }

    public void navigateToTwo() {
        navigator.navigate(TestController2.class);
    }

}
