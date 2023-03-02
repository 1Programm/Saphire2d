package com.programm.ge.saphire2d.reactivevalues.core;

public class StringPropertyValue extends StringProperty {

    private String value;

    public StringPropertyValue() {
        this("");
    }

    public StringPropertyValue(String value) {
        this.value = value;
    }

    @Override
    public String get() {
        return value;
    }

    @Override
    protected void setDirectly(String value) {
        this.value = value;
    }
}
