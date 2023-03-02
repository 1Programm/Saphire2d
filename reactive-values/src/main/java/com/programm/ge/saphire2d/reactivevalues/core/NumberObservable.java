package com.programm.ge.saphire2d.reactivevalues.core;

import com.programm.ge.saphire2d.reactivevalues.ObservableValue;

public interface NumberObservable <T> extends ObservableValue<T> {

    BoolObservable equalTo(ObservableValue<T> value);

    BoolObservable greaterThan(ObservableValue<T> value);

    BoolObservable lessThan(ObservableValue<T> value);

    BoolObservable greaterThanEqualTo(ObservableValue<T> value);

    BoolObservable lessThanEqualTo(ObservableValue<T> value);

    BoolObservable equalTo(T value);

    BoolObservable greaterThan(T value);

    BoolObservable lessThan(T value);

    BoolObservable greaterThanEqualTo(T value);

    BoolObservable lessThanEqualTo(T value);

}
