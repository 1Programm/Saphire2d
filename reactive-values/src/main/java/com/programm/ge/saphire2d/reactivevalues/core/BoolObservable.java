package com.programm.ge.saphire2d.reactivevalues.core;

import com.programm.ge.saphire2d.reactivevalues.ObservableValue;
import com.programm.ge.saphire2d.reactivevalues.expression.bool.AndExpression;
import com.programm.ge.saphire2d.reactivevalues.expression.bool.NotExpression;
import com.programm.ge.saphire2d.reactivevalues.expression.bool.OrExpression;

public interface BoolObservable extends ObservableValue<Boolean> {

    BoolObservable TRUE  = new BoolConst(true);
    BoolObservable FALSE = new BoolConst(false);

    default BoolObservable not()
    {
        return new NotExpression(this);
    }

    default BoolObservable and(ObservableValue<Boolean> other)
    {
        return new AndExpression(this, other);
    }

    default BoolObservable or(ObservableValue<Boolean> other)
    {
        return new OrExpression(this, other);
    }

}
