package com.programm.ge.saphire2d.reactivevalues.expression.bool;

import com.programm.ge.saphire2d.reactivevalues.ObservableValue;

public class EqualsExpression <T> extends BoolExpression {

    private final T value;

    public EqualsExpression(ObservableValue<T> observable, T value) {
        super(observable);
        this.value = value;
    }

    @Override
    protected Boolean express(Object[] values) {
        return values[0].equals(value);
    }
}
