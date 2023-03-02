package com.programm.ge.saphire2d.reactivevalues.expression.string;

import com.programm.ge.saphire2d.reactivevalues.ObservableValue;

public class TrimExpression extends StringExpression {

    public TrimExpression(ObservableValue<String> value) {
        super(value);
    }

    @Override
    protected String express(Object[] values) {
        return ((String)values[0]).trim();
    }
}
