package com.programm.ge.saphire2d.reactivevalues.core;

import com.programm.ge.saphire2d.reactivevalues.EmptyObservable;

public class ObjectConst <T> implements ObjectObservable<T>, EmptyObservable<T> {

    private final T value;

    public ObjectConst() {
        this(null);
    }

    public ObjectConst(T value) {
        this.value = value;
    }

    @Override
    public T get() {
        return value;
    }

}
