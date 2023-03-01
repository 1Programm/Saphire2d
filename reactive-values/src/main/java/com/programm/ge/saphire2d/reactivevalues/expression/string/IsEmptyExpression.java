package com.programm.ge.saphire2d.reactivevalues.expression.string;

import com.programm.ge.saphire2d.reactivevalues.ObservableValue;
import com.programm.ge.saphire2d.reactivevalues.expression.bool.BoolExpression;

public class IsEmptyExpression extends BoolExpression {

    private final ObservableValue<String> value;

    public IsEmptyExpression(ObservableValue<String> value) {
        super(value);
        this.value = value;
    }

    @Override
    public Boolean get() {
        return value.get().isEmpty();
    }
}
