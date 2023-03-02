package com.programm.ge.saphire2d.reactivevalues.core;

public interface OperationalNumberObservable <T extends Comparable<T>> extends NumberObservable<T> {

    void increment();

    void decrement();

    void add(T value);

    void sub(T value);

}
