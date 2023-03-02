package com.programm.ge.saphire2d.reactivevalues.expression.number;

import com.programm.ge.saphire2d.reactivevalues.ObservableValue;
import com.programm.ge.saphire2d.reactivevalues.core.BoolObservable;
import com.programm.ge.saphire2d.reactivevalues.core.IntObservable;
import com.programm.ge.saphire2d.reactivevalues.expression.Expression;

public abstract class IntExpression extends Expression<Integer> implements IntObservable {

    public IntExpression(ObservableValue<?>... values) {
        super(values);
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
