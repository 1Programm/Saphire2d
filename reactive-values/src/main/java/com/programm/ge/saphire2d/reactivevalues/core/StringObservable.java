package com.programm.ge.saphire2d.reactivevalues.core;

import com.programm.ge.saphire2d.reactivevalues.ObservableValue;

public interface StringObservable extends ObservableValue<String> {

    BoolObservable isEmpty();

    StringObservable trim();

    IntObservable size();

}
