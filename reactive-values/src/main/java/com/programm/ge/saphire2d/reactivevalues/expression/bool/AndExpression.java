package com.programm.ge.saphire2d.reactivevalues.expression.bool;

import com.programm.ge.saphire2d.reactivevalues.ObservableValue;

public class AndExpression extends BoolExpression {

    public AndExpression(ObservableValue<Boolean> val1, ObservableValue<Boolean> val2) {
        super(val1, val2);
    }

    @Override
    protected Boolean express(Object[] values) {
        return ((boolean)values[0]) && ((boolean)values[1]);
    }
}
