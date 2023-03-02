package com.programm.ge.saphire2d.reactivevalues.core;

public final class ObjectPropertyValue<T> extends ObjectProperty<T> {

    private T myValue;

    public ObjectPropertyValue(T value)
    {
        myValue = value;
    }

    @Override
    protected void setDirectly(T value)
    {
        myValue = value;
    }

    @Override
    public T get()
    {
        return myValue;
    }
}