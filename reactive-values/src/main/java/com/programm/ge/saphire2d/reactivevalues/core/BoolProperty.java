package com.programm.ge.saphire2d.reactivevalues.core;

import com.programm.ge.saphire2d.reactivevalues.AbstractProperty;
import com.programm.ge.saphire2d.reactivevalues.expression.bool.EqualsExpression;

public abstract class BoolProperty extends AbstractProperty<Boolean> implements BoolObservable {

    public void invert(){
        set(!get());
    }

    @Override
    public BoolObservable equalTo(Boolean value) {
        return new EqualsExpression<>(this, value);
    }
    
}
