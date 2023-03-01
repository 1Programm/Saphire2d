package com.programm.ge.saphire2d.reactivevalues;

import com.programm.ge.saphire2d.reactivevalues.core.ObservableBool;

public interface ObservableValue<T> {

  T get();

  void addListener(ChangeListener listener);

  void removeListener(ChangeListener listener);

  void addWeakListener(ChangeListener listener);

  default ObservableBool equalTo(T value)
  {
    return null;
  }

}
