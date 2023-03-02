package com.programm.ge.saphire2d.reactivevalues;

public interface SettableValue <T> extends ObservableValue<T> {
    void set(T value);

    default void bind(Observable<T> observableValue){
        observableValue.listenChange(this::set);
    }
}
