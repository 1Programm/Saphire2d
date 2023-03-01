package com.programm.ge.saphire2d.reactivevalues.core;

import com.programm.ge.saphire2d.reactivevalues.ChangeListener;

public class ConstString implements ObservableString {

    private final String value;
    private final boolean empty;

    public ConstString() {
        this("");
    }

    public ConstString(String value) {
        this.value = value;
        this.empty = this.value.equals("");
    }

    @Override
    public ObservableBool isEmpty() {
        return new ConstBool(empty);
    }

    @Override
    public ObservableString trim() {
        return new ConstString(value.trim());
    }

    @Override
    public ObservableInt size() {
        return new ConstInt(value.length());
    }

    @Override
    public String get() {
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
