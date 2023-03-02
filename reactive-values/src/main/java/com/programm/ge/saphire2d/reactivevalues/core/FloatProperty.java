package com.programm.ge.saphire2d.reactivevalues.core;

import com.programm.ge.saphire2d.reactivevalues.AbstractProperty;
import com.programm.ge.saphire2d.reactivevalues.ObservableValue;
import com.programm.ge.saphire2d.reactivevalues.expression.number.FloatComparisonExpression;

public abstract class FloatProperty extends AbstractProperty<Float> implements FloatObservable {

    public void increment()
    {
        set(get() + 1);
    }

    public void decrement()
    {
        set(get() - 1);
    }

    @Override
    public BoolObservable equalTo(ObservableValue<Float> value)
    {
        return FloatComparisonExpression.EqualTo(this, value);
    }

    @Override
    public BoolObservable greaterThan(ObservableValue<Float> value)
    {
        return FloatComparisonExpression.GreaterThan(this, value);
    }

    @Override
    public BoolObservable lessThan(ObservableValue<Float> value)
    {
        return FloatComparisonExpression.LessThan(this, value);
    }

    @Override
    public BoolObservable greaterThanEqualTo(ObservableValue<Float> value)
    {
        return FloatComparisonExpression.GreaterThanEqual(this, value);
    }

    @Override
    public BoolObservable lessThanEqualTo(ObservableValue<Float> value)
    {
        return FloatComparisonExpression.LessThanEqual(this, value);
    }

    @Override
    public BoolObservable equalTo(Float value)
    {
        return FloatComparisonExpression.EqualTo(this, value);
    }

    @Override
    public BoolObservable greaterThan(Float value)
    {
        return FloatComparisonExpression.GreaterThan(this, value);
    }

    @Override
    public BoolObservable lessThan(Float value)
    {
        return FloatComparisonExpression.LessThan(this, value);
    }

    @Override
    public BoolObservable greaterThanEqualTo(Float value) {
        return FloatComparisonExpression.GreaterThanEqual(this, value);
    }

    @Override
    public BoolObservable lessThanEqualTo(Float value)
    {
        return FloatComparisonExpression.LessThanEqual(this, value);
    }

}
