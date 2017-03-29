package com.moreflow.android.flow;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.moreflow.android.MoreFlowActivity;
import com.moreflow.android.view.ViewHandler;
import com.moreflow.core.controller.Controller;
import com.moreflow.core.navigation.StateKey;

import flow.Flow;
import flow.Traversal;
import flow.TraversalCallback;

/**
 * Handles replacing views on an activity.
 * Any views this dispatcher handles must have the @ViewMetadata annotation
 */
public class Dispatcher implements flow.Dispatcher {

    private final MoreFlowActivity activity;
    private final ViewHandler viewHandler;

    private boolean entered;

    public Dispatcher(@NonNull MoreFlowActivity activity) {
        this.activity = activity;
        this.viewHandler = new ViewHandler(activity);
    }

    @Override
    public void dispatch(@NonNull Traversal traversal, @NonNull TraversalCallback callback) {
        Object key = traversal.destination.top();

        Log.d("cueme", "state change: " + key.toString());

        if (!viewHandler.initializedResolvers()) {
            //loading view??
            viewHandler.initializeResolvers();
        }

        if (checkInOrOutState(key, traversal, callback)) {
            return;
        }

        Log.d("cueme", "got past initial");

        Class<Controller> controllerClass = getController(key);
        Object state = getState(key);

        ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);

        if (cleanupOldViewCheckSameKey(key, traversal, callback, contentView)) return;

        View incomingView = viewHandler.buildView(key, controllerClass, state, traversal, contentView);
        contentView.addView(incomingView);

        traversal.getState(traversal.destination.top()).restore(incomingView);
        callback.onTraversalCompleted();
    }

    private boolean cleanupOldViewCheckSameKey(Object destKey, Traversal traversal, TraversalCallback callback, ViewGroup contentView) {
        // We're already showing something, clean it up.
        if (contentView.getChildCount() > 0) {
            final View currentView = contentView.getChildAt(0);

            // Save the outgoing view state.
            if (traversal.origin != null) {
                traversal.getState(traversal.origin.top()).save(currentView);
            }

            // Short circuit if we would just be showing the same view again.
            final Object currentKey = Flow.getKey(currentView);
            if (destKey.equals(currentKey)) {
                callback.onTraversalCompleted();
                return true;
            }

            viewHandler.cleanupNavigator(currentView);
            contentView.removeViewAt(0);
        }
        return false;
    }

    private boolean checkInOrOutState(Object key, Traversal traversal, TraversalCallback callback) {
        if (key == InitialState.class && !entered) {
            entered = true;
            View view = new View(activity);
            activity.setContentView(view);
            traversal.getState(traversal.destination.top()).restore(view);
            callback.onTraversalCompleted();
            return true;
        } else if (key == InitialState.class && entered) {
            callback.onTraversalCompleted();
            activity.onBackPressed();
            return true;
        }
        return false;
    }

    private Class<Controller> getController(Object key) {
        return key instanceof StateKey ? ((StateKey) key).getController() : (Class<Controller>) key;
    }

    private Object getState(Object key) {
        return key instanceof StateKey ? ((StateKey) key).getState() : null;
    }

}
