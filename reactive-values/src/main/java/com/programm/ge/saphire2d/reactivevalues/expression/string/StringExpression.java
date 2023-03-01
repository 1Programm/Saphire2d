package com.programm.ge.saphire2d.reactivevalues.expression.string;

import com.programm.ge.saphire2d.reactivevalues.ObservableValue;
import com.programm.ge.saphire2d.reactivevalues.core.ObservableBool;
import com.programm.ge.saphire2d.reactivevalues.core.ObservableInt;
import com.programm.ge.saphire2d.reactivevalues.core.ObservableString;
import com.programm.ge.saphire2d.reactivevalues.expression.Expression;

public abstract class StringExpression extends Expression<String> implements ObservableString {

    protected StringExpression(ObservableValue<?>... values){
        super(values);
    }

    @Override
    public ObservableBool isEmpty() {
        return new IsEmptyExpression(this);
    }

    @Override
    public ObservableString trim() {
        return new TrimExpression(this);
    }

    @Override
    public ObservableInt size() {
        return new SizeExpression(this);
    }
}
