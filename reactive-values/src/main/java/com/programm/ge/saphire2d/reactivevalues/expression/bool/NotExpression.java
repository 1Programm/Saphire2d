package com.programm.ge.saphire2d.reactivevalues.expression.bool;

import com.programm.ge.saphire2d.reactivevalues.ObservableValue;

public class NotExpression extends BoolExpression {

    private final ObservableValue<Boolean> value;

    public NotExpression(ObservableValue<Boolean> value) {
        super(value);
        this.value = value;
    }

    @Override
    public Boolean get() {
        return !value.get();
    }
}
