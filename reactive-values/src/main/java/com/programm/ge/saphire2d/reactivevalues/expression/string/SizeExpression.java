package com.programm.ge.saphire2d.reactivevalues.expression.string;

import com.programm.ge.saphire2d.reactivevalues.ObservableValue;
import com.programm.ge.saphire2d.reactivevalues.expression.number.IntExpression;

public class SizeExpression extends IntExpression {

    public SizeExpression(ObservableValue<String> value) {
        super(value);
    }

    @Override
    protected Integer express(Object[] values) {
        return ((String)values[0]).length();
    }
}
