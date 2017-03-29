package com.moreflow.core.resolver;

public interface IDependencyResolver<T> {

    T resolve(Class<T> clazz);

}
