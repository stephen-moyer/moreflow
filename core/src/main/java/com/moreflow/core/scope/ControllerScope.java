package com.moreflow.core.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ControllerScope {

    Class<? extends Scope> value();

}
