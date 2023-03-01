package com.programm.ge.saphire2d.reactivevalues.core;

public class IntValueProperty extends IntProperty {

    private Integer value;

    public IntValueProperty() {
        this(0);
    }

    public IntValueProperty(Integer value) {
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
