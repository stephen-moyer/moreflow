package com.moreflow.core.navigation;

import com.moreflow.core.controller.Controller;

/**
 * TODO This probably shouldn't be handling the pushMessageUp/down functionality.
 */
public interface INavigator {

    void navigate(Class<? extends Controller> controllerClass);

    void navigate(Class<? extends Controller> controllerClass, Object state);

    void pushMessageUp(int messageId, Object value);

    void pushMessageUp(int messageId);

    void pushMessageDown(int messageId, Object value);

    void pushMessageDown(int messageId);

    void goBack();

}
