package com.programm.ge.saphire2d.reactivevalues;

import com.programm.ge.saphire2d.reactivevalues.core.BoolObservable;

public interface ObservableValue<T> extends Observable {

  T get();

  default BoolObservable equalTo(T value)
  {
    return null;
  }

}
