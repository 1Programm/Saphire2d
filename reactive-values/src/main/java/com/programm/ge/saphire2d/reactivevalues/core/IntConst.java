package com.programm.ge.saphire2d.reactivevalues.core;

import com.programm.ge.saphire2d.reactivevalues.ChangeListener;
import com.programm.ge.saphire2d.reactivevalues.ObservableValue;
import com.programm.ge.saphire2d.reactivevalues.expression.number.IntComparisonExpression;

public class IntConst implements IntObservable {

    private final int value;

    public IntConst(int value)
    {
        this.value = value;
    }

    @Override
    public Integer get()
    {
        return value;
    }

    @Override
    public void addListener(ChangeListener listener)
    {

    }

    @Override
    public void removeListener(ChangeListener listener)
    {

    }

    @Override
    public void addWeakListener(ChangeListener listener)
    {

    }

    @Override
    public BoolObservable equalTo(ObservableValue<Integer> value)
    {
        return IntComparisonExpression.EqualTo(this, value);
    }

    @Override
    public BoolObservable greaterThan(ObservableValue<Integer> value)
    {
        return IntComparisonExpression.GreaterThan(this, value);
    }

    @Override
    public BoolObservable lessThan(ObservableValue<Integer> value)
    {
        return IntComparisonExpression.LessThan(this, value);
    }

    @Override
    public BoolObservable greaterThanEqualTo(ObservableValue<Integer> value)
    {
        return IntComparisonExpression.GreaterThanEqual(this, value);
    }

    @Override
    public BoolObservable lessThanEqualTo(ObservableValue<Integer> value)
    {
        return IntComparisonExpression.LessThanEqual(this, value);
    }

    @Override
    public BoolObservable equalTo(Integer value)
    {
        return IntComparisonExpression.EqualTo(this, value);
    }

    @Override
    public BoolObservable greaterThan(Integer value)
    {
        return IntComparisonExpression.GreaterThan(this, value);
    }

    @Override
    public BoolObservable lessThan(Integer value)
    {
        return IntComparisonExpression.LessThan(this, value);
    }

    @Override
    public BoolObservable greaterThanEqualTo(Integer value)
    {
        return IntComparisonExpression.GreaterThanEqual(this, value);
    }

    @Override
    public BoolObservable lessThanEqualTo(Integer value)
    {
        return IntComparisonExpression.LessThanEqual(this, value);
    }

}
