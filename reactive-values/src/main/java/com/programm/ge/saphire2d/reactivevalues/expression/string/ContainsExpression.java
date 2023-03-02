package com.programm.ge.saphire2d.reactivevalues.expression.string;

import com.programm.ge.saphire2d.reactivevalues.ObservableValue;
import com.programm.ge.saphire2d.reactivevalues.core.StringConst;
import com.programm.ge.saphire2d.reactivevalues.expression.bool.BoolExpression;

public class ContainsExpression extends BoolExpression {

    private final ObservableValue<String> value;
    private final ObservableValue<String> toContain;

    public ContainsExpression(ObservableValue<String> value, String toContain) {
        this(value, new StringConst(toContain));
    }

    public ContainsExpression(ObservableValue<String> value, ObservableValue<String> toContain) {
        super(value);
        this.value = value;
        this.toContain = toContain;
    }

    @Override
    public Boolean get() {
        String sValue = value.get();
        String sContain = toContain.get();
        return sValue.contains(sContain);
    }
}
