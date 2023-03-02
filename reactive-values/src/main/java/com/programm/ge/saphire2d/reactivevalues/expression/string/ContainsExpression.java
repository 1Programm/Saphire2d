package com.programm.ge.saphire2d.reactivevalues.expression.string;

import com.programm.ge.saphire2d.reactivevalues.ObservableValue;
import com.programm.ge.saphire2d.reactivevalues.core.StringConst;
import com.programm.ge.saphire2d.reactivevalues.expression.bool.BoolExpression;

public class ContainsExpression extends BoolExpression {

    public ContainsExpression(ObservableValue<String> value, String toContain) {
        this(value, new StringConst(toContain));
    }

    public ContainsExpression(ObservableValue<String> value, ObservableValue<String> toContain) {
        super(value);
    }

    @Override
    protected Boolean express(Object[] values) {
        return ((String)values[0]).contains((String)values[1]);
    }
}
