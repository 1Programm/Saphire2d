package com.programm.ge.saphire2d.reactivevalues.core;

import com.programm.ge.saphire2d.reactivevalues.ChangeListener;

public class StringConst implements StringObservable {

    private final String value;
    private final boolean empty;

    public StringConst() {
        this("");
    }

    public StringConst(String value) {
        this.value = value;
        this.empty = this.value.equals("");
    }

    @Override
    public BoolObservable isEmpty() {
        return new BoolConst(empty);
    }

    @Override
    public StringObservable trim() {
        return new StringConst(value.trim());
    }

    @Override
    public IntObservable size() {
        return new IntConst(value.length());
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
