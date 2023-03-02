package com.programm.ge.saphire2d.reactivevalues;

import com.programm.ge.saphire2d.reactivevalues.core.BoolObservable;

public interface ObservableValue<T> extends Observable<T> {

    T get();

//  void addListener(ChangeListener<T> listener);
//
//  void removeListener(ChangeListener<T> listener);
//
//  void addWeakListener(ChangeListener<T> listener);

    default BoolObservable equalTo(T value)
    {
        return null;
    }

}
