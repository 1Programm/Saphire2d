package com.programm.ge.saphire2d.reactivevalues.core;

import com.programm.ge.saphire2d.reactivevalues.AbstractProperty;
import com.programm.ge.saphire2d.reactivevalues.expression.string.IsEmptyExpression;
import com.programm.ge.saphire2d.reactivevalues.expression.string.SizeExpression;
import com.programm.ge.saphire2d.reactivevalues.expression.string.TrimExpression;

public abstract class StringProperty extends AbstractProperty<String> implements StringObservable {

    public void clear(){
        set("");
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
