package com.zjxjd.manage.common.redis;

public interface Function<E, T> {

    public T callback(E e);

}
