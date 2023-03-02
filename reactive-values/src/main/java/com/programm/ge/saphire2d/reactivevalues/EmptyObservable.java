package com.programm.ge.saphire2d.reactivevalues;

public interface EmptyObservable<T> extends Observable<T> {

    @Override
    default void listenChange(ChangeListener<T> listener) {}

    @Override
    default void listen(NotifyListener listener) {}

    @Override
    default void removeListener(ChangeListener<T> listener){}

//    void addWeakListener(ChangeListener<T> listener);

}
