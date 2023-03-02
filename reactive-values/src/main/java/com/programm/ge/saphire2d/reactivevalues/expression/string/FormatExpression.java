package com.programm.ge.saphire2d.reactivevalues.expression.string;

import com.programm.ge.saphire2d.reactivevalues.ObservableValue;

import java.util.Arrays;
import java.util.List;

public final class FormatExpression extends StringExpression {

    private final String myFormatString;

    public FormatExpression(String formatString, ObservableValue<?>... values) {
        super(values);
        myFormatString = formatString;
    }

    @Override
    protected String express(Object[] values) {
        return String.format(myFormatString, values);
    }
}
