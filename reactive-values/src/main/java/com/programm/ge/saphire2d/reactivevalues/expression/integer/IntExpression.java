package com.programm.ge.saphire2d.reactivevalues.expression.integer;

import com.programm.ge.saphire2d.reactivevalues.ObservableValue;
import com.programm.ge.saphire2d.reactivevalues.core.ObservableBool;
import com.programm.ge.saphire2d.reactivevalues.core.ObservableInt;
import com.programm.ge.saphire2d.reactivevalues.expression.Expression;

public abstract class IntExpression extends Expression<Integer> implements ObservableInt {

  public IntExpression(ObservableValue<?>... values)
  {
    super(values);
  }

  @Override
  public ObservableBool equalTo(ObservableValue<Integer> value)
  {
    return IntComparisonExpression.EqualTo(this, value);
  }

  @Override
  public ObservableBool greaterThan(ObservableValue<Integer> value)
  {
    return IntComparisonExpression.GreaterThan(this, value);
  }

  @Override
  public ObservableBool lessThan(ObservableValue<Integer> value)
  {
    return IntComparisonExpression.LessThan(this, value);
  }

  @Override
  public ObservableBool greaterThanEqualTo(ObservableValue<Integer> value)
  {
    return IntComparisonExpression.GreaterThanEqual(this, value);
  }

  @Override
  public ObservableBool lessThanEqualTo(ObservableValue<Integer> value)
  {
    return IntComparisonExpression.LessThanEqual(this, value);
  }

  @Override
  public ObservableBool equalTo(int value)
  {
    return IntComparisonExpression.EqualTo(this, value);
  }

  @Override
  public ObservableBool greaterThan(int value)
  {
    return IntComparisonExpression.GreaterThan(this, value);
  }

  @Override
  public ObservableBool lessThan(int value)
  {
    return IntComparisonExpression.LessThan(this, value);
  }

  @Override
  public ObservableBool greaterThanEqualTo(int value)
  {
    return IntComparisonExpression.GreaterThanEqual(this, value);
  }

  @Override
  public ObservableBool lessThanEqualTo(int value)
  {
    return IntComparisonExpression.LessThanEqual(this, value);
  }

}
