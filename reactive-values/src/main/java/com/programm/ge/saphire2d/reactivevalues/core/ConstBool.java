package com.programm.ge.saphire2d.reactivevalues.core;

import com.programm.ge.saphire2d.reactivevalues.ChangeListener;

public class ConstBool implements ObservableBool {

    private final boolean value;

    public ConstBool() {
        this(false);
    }

    public ConstBool(boolean value) {
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
