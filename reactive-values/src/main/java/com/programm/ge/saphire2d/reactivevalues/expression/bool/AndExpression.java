package com.programm.ge.saphire2d.reactivevalues.expression.bool;

import com.programm.ge.saphire2d.reactivevalues.ObservableValue;

public class AndExpression extends BoolExpression {

    private final ObservableValue<Boolean> val1;
    private final ObservableValue<Boolean> val2;

    public AndExpression(ObservableValue<Boolean> val1, ObservableValue<Boolean> val2) {
        super(val1, val2);
        this.val1 = val1;
        this.val2 = val2;
    }

    @Override
    public Boolean get() {
        return val1.get() && val2.get();
    }
}
