package com.programm.ge.saphire2d.reactivevalues.core;

public class IntPropertyValue extends IntProperty {

    private Integer value;

    public IntPropertyValue() {
        this(0);
    }

    public IntPropertyValue(Integer value) {
        this.value = value;
    }

    @Override
    public Integer get() {
        return value;
    }

    @Override
    protected void setDirectly(Integer value) {
        this.value = value;
    }
}
