package com.programm.ge.saphire2d.reactivevalues.core;

import com.programm.ge.saphire2d.reactivevalues.EmptyObservable;

public class StringConst implements StringObservable, EmptyObservable<String> {

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

}
