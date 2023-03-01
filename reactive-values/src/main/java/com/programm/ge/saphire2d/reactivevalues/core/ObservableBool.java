package com.programm.ge.saphire2d.reactivevalues.core;

import com.programm.ge.saphire2d.reactivevalues.ObservableValue;
import com.programm.ge.saphire2d.reactivevalues.expression.bool.AndExpression;
import com.programm.ge.saphire2d.reactivevalues.expression.bool.NotExpression;
import com.programm.ge.saphire2d.reactivevalues.expression.bool.OrExpression;

public interface ObservableBool extends ObservableValue<Boolean>
{
  ObservableBool TRUE  = new ConstBool(true);
  ObservableBool FALSE = new ConstBool(false);

  default ObservableBool not()
  {
    return new NotExpression(this);
  }

  default ObservableBool and(ObservableValue<Boolean> other)
  {
    return new AndExpression(this, other);
  }

  default ObservableBool or(ObservableValue<Boolean> other)
  {
    return new OrExpression(this, other);
  }

}
