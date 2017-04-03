package com.moreflow.android.view;

import com.moreflow.android.MoreFlowActivity;
import com.moreflow.core.resolver.IDependencyResolver;
import com.moreflow.core.view.IView;

import java.lang.reflect.InvocationTargetException;

/**
 * Default view resolver that just looks for the constructor with a MoreFlowActivity parameter
 * If that's not found, clazz.newInstance is called.
 */
public class BasicViewResolver implements IDependencyResolver<IView> {

    private final MoreFlowActivity activity;

    public BasicViewResolver(MoreFlowActivity activity) {
        this.activity = activity;
    }

    @Override
    public IView resolve(Class<IView> clazz) {
        try {
            return clazz.getConstructor(MoreFlowActivity.class).newInstance(activity);
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
