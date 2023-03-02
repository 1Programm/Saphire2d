package com.programm.ge.saphire2d.reactivevalues.core;

import com.programm.ge.saphire2d.reactivevalues.ChangeListener;

public class BoolConst implements BoolObservable {

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

    @Override
    public void addListener(ChangeListener listener) {

    }

    @Override
    public void removeListener(ChangeListener listener) {

    }

    @Override
    public void addWeakListener(ChangeListener listener) {

    }
}
