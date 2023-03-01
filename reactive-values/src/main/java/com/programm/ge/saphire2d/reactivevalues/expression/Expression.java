package com.programm.ge.saphire2d.reactivevalues.expression;

import com.programm.ge.saphire2d.reactivevalues.AbstractObservableValue;
import com.programm.ge.saphire2d.reactivevalues.ChangeListener;
import com.programm.ge.saphire2d.reactivevalues.ObservableValue;

public abstract class Expression<T> extends AbstractObservableValue<T> implements ObservableValue<T> {

    protected Expression(ObservableValue<?>... values){
        if(values.length == 0){
            throw new IllegalArgumentException("Can't create an Expression without arguments!");
        }

        ChangeListener myListener = this::notifyChange;
        for(ObservableValue<?> val : values){
            val.addWeakListener(myListener);
        }
    }

}
