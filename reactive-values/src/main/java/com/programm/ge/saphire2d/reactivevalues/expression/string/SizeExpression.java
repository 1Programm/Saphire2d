package com.programm.ge.saphire2d.reactivevalues.expression.string;

import com.programm.ge.saphire2d.reactivevalues.ObservableValue;
import com.programm.ge.saphire2d.reactivevalues.expression.integer.IntExpression;

public class SizeExpression extends IntExpression {

    private final ObservableValue<String> value;

    public SizeExpression(ObservableValue<String> value) {
        super(value);
        this.value = value;
    }

    @Override
    public Integer get() {
        return value.get().length();
    }
}
