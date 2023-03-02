package com.programm.ge.saphire2d.reactivevalues.expression.string;

import com.programm.ge.saphire2d.reactivevalues.ObservableValue;

import java.util.Arrays;
import java.util.List;

public final class FormatExpression extends StringExpression {

    private final List<ObservableValue<?>> myValues;
    private final String myFormatString;

    public FormatExpression(String formatString, ObservableValue<?>... values) {
        super(values);
        myFormatString = formatString;
        myValues = Arrays.asList(values);
    }

    @Override
    public String get() {
        Object[] values = new Object[myValues.size()];
        int i = 0;
        for(ObservableValue<?> observableValue : myValues) {
            values[i++] = observableValue.get();
        }
        return String.format(myFormatString, values);
    }
}
