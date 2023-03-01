package com.programm.ge.saphire2d.reactivevalues.adapter;

import com.programm.ge.saphire2d.reactivevalues.AbstractProperty;

public class StringToIntAdapterProperty extends AdapterProperty<String, Integer> {

    public StringToIntAdapterProperty(AbstractProperty<String> wrappedPropery) {
        super(wrappedPropery, 0);
    }

    @Override
    protected Integer convertSrcToDest(String value) {
        value = value.trim();
        try {
            return Integer.parseInt(value);
        }
        catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    protected String convertDestToSrc(Integer value) {
        return Integer.toString(value);
    }

}
