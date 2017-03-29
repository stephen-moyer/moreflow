package com.moreflow.core.scope;

public abstract class Scope {

    public boolean canLeave() {
        return true;
    }

    public boolean canMoveTo(Scope toScope) {
        return true;
    }

}
