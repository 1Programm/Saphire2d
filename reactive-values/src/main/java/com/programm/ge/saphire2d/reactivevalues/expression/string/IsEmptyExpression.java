package com.programm.ge.saphire2d.reactivevalues.expression.string;

import com.programm.ge.saphire2d.reactivevalues.ObservableValue;
import com.programm.ge.saphire2d.reactivevalues.expression.bool.BoolExpression;

public class IsEmptyExpression extends BoolExpression {

    public IsEmptyExpression(ObservableValue<String> value) {
        super(value);
    }

    @Override
    protected Boolean express(Object[] values) {
        return ((String)values[0]).isEmpty();
    }
}
