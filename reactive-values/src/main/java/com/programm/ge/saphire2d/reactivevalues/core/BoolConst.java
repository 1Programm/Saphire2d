package com.programm.ge.saphire2d.reactivevalues.core;

import com.programm.ge.saphire2d.reactivevalues.EmptyObservable;

public class BoolConst implements BoolObservable, EmptyObservable<Boolean> {

    private final boolean value;

    public BoolConst() {
        this(false);
    }

    public BoolConst(boolean value) {
        this.value = value;
    }

    @Override
    public Boolean get() {
        return value;
    }

}
