package com.programm.ge.saphire2d.reactivevalues.core;

import com.programm.ge.saphire2d.reactivevalues.ObservableValue;

public interface ObservableString extends ObservableValue<String> {

    ObservableBool isEmpty();

    ObservableString trim();

    ObservableInt size();

}
