package com.programm.ge.saphire2d.reactivevalues.core;

import com.programm.ge.saphire2d.reactivevalues.AbstractProperty;
import com.programm.ge.saphire2d.reactivevalues.ObservableValue;
import com.programm.ge.saphire2d.reactivevalues.expression.number.IntComparisonExpression;

public abstract class IntProperty extends AbstractProperty<Integer> implements IntObservable, OperationalNumberObservable<Integer> {

    @Override
    public void increment() {
        set(get() + 1);
    }

    @Override
    public void decrement() {
        set(get() - 1);
    }

    @Override
    public void add(Integer value) {
        set(get() + value);
    }

    @Override
    public void sub(Integer value) {
        set(get() - value);
    }

    @Override
    public BoolObservable equalTo(ObservableValue<Integer> value) {
        return IntComparisonExpression.EqualTo(this, value);
    }

    @Override
    public BoolObservable greaterThan(ObservableValue<Integer> value) {
        return IntComparisonExpression.GreaterThan(this, value);
    }

    @Override
    public BoolObservable lessThan(ObservableValue<Integer> value) {
        return IntComparisonExpression.LessThan(this, value);
    }

    @Override
    public BoolObservable greaterThanEqualTo(ObservableValue<Integer> value) {
        return IntComparisonExpression.GreaterThanEqual(this, value);
    }

    @Override
    public BoolObservable lessThanEqualTo(ObservableValue<Integer> value) {
        return IntComparisonExpression.LessThanEqual(this, value);
    }

    @Override
    public BoolObservable equalTo(Integer value) {
        return IntComparisonExpression.EqualTo(this, value);
    }

    @Override
    public BoolObservable greaterThan(Integer value) {
        return IntComparisonExpression.GreaterThan(this, value);
    }

    @Override
    public BoolObservable lessThan(Integer value) {
        return IntComparisonExpression.LessThan(this, value);
    }

    @Override
    public BoolObservable greaterThanEqualTo(Integer value) {
        return IntComparisonExpression.GreaterThanEqual(this, value);
    }

    @Override
    public BoolObservable lessThanEqualTo(Integer value) {
        return IntComparisonExpression.LessThanEqual(this, value);
    }

}
