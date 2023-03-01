package com.programm.ge.saphire2d.reactivevalues.expression.string;

import com.programm.ge.saphire2d.reactivevalues.ObservableValue;

public class TrimExpression extends StringExpression {

    private final ObservableValue<String> value;

    public TrimExpression(ObservableValue<String> value) {
        super(value);
        this.value = value;
    }

    @Override
    public String get() {
        return value.get().trim();
    }
}
