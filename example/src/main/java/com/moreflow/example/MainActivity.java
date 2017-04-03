package com.moreflow.example;

import android.support.annotation.NonNull;
import android.os.Bundle;

import com.moreflow.android.MoreFlowActivity;
import com.moreflow.android.view.BasicViewResolver;
import com.moreflow.core.resolver.IDependencyResolver;
import com.moreflow.core.resolver.IControllerViewResolver;
import com.moreflow.core.view.IView;
import com.moreflow.core.controller.Controller;
import com.moreflow.core.controller.BasicControllerResolver;
import com.moreflow.example.test.TestController;
import com.moreflow.generated.ViewControllerResolver_Generated;

public class MainActivity extends MoreFlowActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public IDependencyResolver<Controller> controllerResolver() {
        return new BasicControllerResolver(this.getMoreFlow());
    }

    @NonNull
    @Override
    public IDependencyResolver<IView> viewResolver() {
        return new BasicViewResolver(this);
    }

    @NonNull
    @Override
    public IControllerViewResolver controllerViewResolver() {
        return new ViewControllerResolver_Generated();
    }

    @Override
    protected Object defaultKey() {
        return TestController.class;
    }

}
