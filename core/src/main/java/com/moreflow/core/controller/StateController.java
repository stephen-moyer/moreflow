package com.moreflow.core.controller;

import com.moreflow.core.view.IView;

public abstract class StateController<T extends IView, S> extends Controller<T> {

    private S state;

    @Override
    public final void viewAttached() {}

    public void viewAttached(S state) {
        this.state = state;
        this.viewWithStateAttached(state);
    }

    protected abstract void viewWithStateAttached(S state);

    public S getState() {
        return state;
    }

}
