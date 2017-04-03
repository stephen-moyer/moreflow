package com.moreflow.core.controller;

import com.moreflow.core.MoreFlow;
import com.moreflow.core.resolver.IDependencyResolver;

import java.lang.reflect.InvocationTargetException;

/**
 * Default controller resolver that just looks for the constructor with a MoreFlow parameter
 * If that's not found, clazz.newInstance is called.
 */
public class BasicControllerResolver implements IDependencyResolver<Controller> {

    private final MoreFlow moreFlow;

    public BasicControllerResolver(MoreFlow moreFlow) {
        this.moreFlow = moreFlow;
    }

    @Override
    public Controller resolve(Class<Controller> clazz) {
        try {
            return clazz.getConstructor(Controller.class).newInstance(moreFlow);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        try {
            return clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}
