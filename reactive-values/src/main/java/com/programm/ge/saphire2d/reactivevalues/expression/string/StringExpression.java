package com.programm.ge.saphire2d.reactivevalues.expression.string;

import com.programm.ge.saphire2d.reactivevalues.ObservableValue;
import com.programm.ge.saphire2d.reactivevalues.core.BoolObservable;
import com.programm.ge.saphire2d.reactivevalues.core.IntObservable;
import com.programm.ge.saphire2d.reactivevalues.core.StringObservable;
import com.programm.ge.saphire2d.reactivevalues.expression.Expression;

public abstract class StringExpression extends Expression<String> implements StringObservable {

    protected StringExpression(ObservableValue<?>... values){
        super(values);
    }

    @Override
    public BoolObservable isEmpty() {
        return new IsEmptyExpression(this);
    }

    @Override
    public StringObservable trim() {
        return new TrimExpression(this);
    }

    @Override
    public IntObservable size() {
        return new SizeExpression(this);
    }
}
