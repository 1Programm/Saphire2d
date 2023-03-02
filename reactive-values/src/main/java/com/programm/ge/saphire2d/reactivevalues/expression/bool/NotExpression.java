package com.programm.ge.saphire2d.reactivevalues.expression.bool;

import com.programm.ge.saphire2d.reactivevalues.ObservableValue;

public class NotExpression extends BoolExpression {

    public NotExpression(ObservableValue<Boolean> value) {
        super(value);
    }

    @Override
    protected Boolean express(Object[] values) {
        return !((boolean)values[0]);
    }
}
