package com.programm.ge.saphire2d.reactivevalues.expression.bool;

import com.programm.ge.saphire2d.reactivevalues.ObservableValue;
import com.programm.ge.saphire2d.reactivevalues.core.ObservableBool;
import com.programm.ge.saphire2d.reactivevalues.expression.Expression;

public abstract class BoolExpression extends Expression<Boolean> implements ObservableBool {

    public BoolExpression(ObservableValue<?>... values) {
        super(values);
    }
}
