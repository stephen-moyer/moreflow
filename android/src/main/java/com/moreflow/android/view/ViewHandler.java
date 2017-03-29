package com.moreflow.android.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moreflow.android.MoreFlowActivity;
import com.moreflow.android.flow.Navigator;

import com.moreflow.core.controller.Controller;
import com.moreflow.core.controller.StateController;
import com.moreflow.core.resolver.IControllerViewResolver;
import com.moreflow.core.view.IView;
import com.moreflow.core.resolver.IDependencyResolver;

import flow.Flow;
import flow.Traversal;

public class ViewHandler {

    private final MoreFlowActivity activity;

    private IDependencyResolver<Controller> controllerResolver;
    private IDependencyResolver<IView> viewResolver;
    private IControllerViewResolver controllerViewResolver;

    public ViewHandler(MoreFlowActivity activity) {
        this.activity = activity;
    }

    public boolean initializedResolvers() {
        return controllerResolver != null && viewResolver != null && controllerViewResolver != null;
    }

    public void initializeResolvers() {
        controllerResolver = activity.controllerResolver();
        viewResolver = activity.viewResolver();
        controllerViewResolver = activity.controllerViewResolver();

        if (controllerResolver == null) {
            throw new NullPointerException("Controller resolver cannot be null");
        }
        if (viewResolver == null) {
            throw new NullPointerException("View resolver cannot be null");
        }
        if (controllerViewResolver == null) {
            throw new NullPointerException("Controller view resolver cannot be null");
        }
    }

    public void cleanupNavigator(View view) {
        Navigator navigator = (Navigator) view.getTag();
        if (navigator == null) return;
        cleanupNavigator(navigator);
    }

    private void cleanupNavigator(Navigator navigator) {
        Controller controller = navigator.getController();
        ((IAndroidView)navigator.getController().getView()).viewDestroyed();
        controller.viewDetached();
        Navigator[] children = navigator.getChildren();
        if (children == null) return;
        for(int i = 0; i < children.length; i++) {
            Navigator childNavigator = children[i];
            if (childNavigator == null) continue;
            cleanupNavigator(navigator);
        }
    }

    public View buildView(Object key, Class<Controller> controllerClass, Object state, Traversal traversal, ViewGroup contentView) {
        ViewMetadata metadata = getViewMetadataForController(controllerClass);

        View rootView = LayoutInflater.from(traversal.createContext(key, activity)).inflate(metadata.layoutId(), contentView, false);

        Navigator navigator = initializeView(controllerClass, state, rootView, Flow.get(rootView), null);
        rootView.setTag(navigator);

        return rootView;
    }

    private Navigator initializeView(Class<Controller> controllerClass, Object state, View androidView, Flow flow, Navigator parentNavigator) {
        Controller controller = controllerResolver.resolve(controllerClass);
        //TODO add annotation processing for this.
        IView view = viewResolver.resolve((Class<IView>) controllerViewResolver.viewForController(controllerClass));
        if (!(view instanceof IAndroidView)) {
            throw new RuntimeException("Views on android must implement IAndroidView");
        }

        Navigator navigator;
        //initialize the children
        ViewMetadata metadata = getViewMetadataForController(controllerClass);
        if (metadata != null) {
            ViewChildMetadata[] children = metadata.children();
            Navigator[] childNavigators = children.length > 0 ? new Navigator[children.length] : null;
            navigator = new Navigator(flow, activity, controller, parentNavigator, childNavigators);
            for (int i = 0; i < children.length; i++) {
                ViewChildMetadata childMetadata = children[i];
                View childView = androidView.findViewById(childMetadata.viewId());
                childNavigators[i] = initializeView(childMetadata.controller(), null, childView, flow, navigator);
            }
        } else {
            navigator = new Navigator(flow, activity, controller, parentNavigator, null);
        }

        //set ourself up now
        ((IAndroidView) view).viewLoaded(androidView, controller);

        //TODO Pool for navigator??
        controller.setNavigator(navigator);
        controller.setView(view);

        //TODO this is ugly
        if (controller instanceof StateController) {
            ((StateController) controller).viewAttached(state);
        } else {
            controller.viewAttached();
        }

        return navigator;
    }

    private ViewMetadata getViewMetadataForController(Class<Controller> controllerClass) {
        return controllerViewResolver.viewForController(controllerClass).getAnnotation(ViewMetadata.class);
    }

}
