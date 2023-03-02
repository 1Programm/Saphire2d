package com.programm.ge.saphire2d.reactivevalues.core;

public class FloatPropertyValue extends FloatProperty {

    private Float value;

    public FloatPropertyValue() {
        this(0f);
    }

    public FloatPropertyValue(Float value) {
        this.value = value;
    }

    @Override
    public Float get() {
        return value;
    }

    @Override
    protected void setDirectly(Float value) {
        this.value = value;
    }
}
