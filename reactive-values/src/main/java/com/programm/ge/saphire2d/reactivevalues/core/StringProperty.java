package com.programm.ge.saphire2d.reactivevalues.core;

import com.programm.ge.saphire2d.reactivevalues.AbstractProperty;
import com.programm.ge.saphire2d.reactivevalues.expression.string.IsEmptyExpression;
import com.programm.ge.saphire2d.reactivevalues.expression.string.SizeExpression;
import com.programm.ge.saphire2d.reactivevalues.expression.string.TrimExpression;

public abstract class StringProperty extends AbstractProperty<String> implements ObservableString {

    public void clear(){
        set("");
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
