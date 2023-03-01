package com.programm.ge.saphire2d.reactivevalues.core;

import com.programm.ge.saphire2d.reactivevalues.ObservableValue;

public interface ObservableInt extends ObservableValue<Integer>
{

  ObservableBool equalTo(ObservableValue<Integer> value);

  ObservableBool greaterThan(ObservableValue<Integer> value);

  ObservableBool lessThan(ObservableValue<Integer> value);

  ObservableBool greaterThanEqualTo(ObservableValue<Integer> value);

  ObservableBool lessThanEqualTo(ObservableValue<Integer> value);

  ObservableBool equalTo(int value);

  ObservableBool greaterThan(int value);

  ObservableBool lessThan(int value);

  ObservableBool greaterThanEqualTo(int value);

  ObservableBool lessThanEqualTo(int value);

}
