package com.programm.ge.saphire2d.reactivevalues;

import com.programm.ge.saphire2d.reactivevalues.core.BoolObservable;

public interface ObservableValue<T> {

  T get();

  void addListener(ChangeListener listener);

  void removeListener(ChangeListener listener);

  void addWeakListener(ChangeListener listener);

  default BoolObservable equalTo(T value)
  {
    return null;
  }

}
