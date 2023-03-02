package com.programm.ge.saphire2d.reactivevalues.core;

public class BoolPropertyValue extends BoolProperty {

    private boolean value;

    public BoolPropertyValue()
    {
        this(false);
    }

    public BoolPropertyValue(boolean value)
    {
        this.value = value;
    }

    @Override
    public Boolean get()
    {
        return value;
    }

    @Override
    protected void setDirectly(Boolean value)
    {
        this.value = value;
    }

}
