package com.moreflow.core.resolver;

import com.moreflow.core.controller.Controller;
import com.moreflow.core.view.IView;

public interface IControllerViewResolver {

    Class<? extends IView> viewForController(Class<? extends Controller> controllerClass);

}
