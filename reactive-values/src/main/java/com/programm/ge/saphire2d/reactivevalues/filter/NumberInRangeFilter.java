package com.programm.ge.saphire2d.reactivevalues.filter;

import com.programm.ge.saphire2d.reactivevalues.Observable;

public class NumberInRangeFilter<T extends Comparable<T>> extends NumberFilter<T> {

    private final T min;
    private final T max;

    public NumberInRangeFilter(Observable<T> observable, T min, T max) {
        super(observable);
        this.min = min;
        this.max = max;
    }

    @Override
    protected boolean filter(T val) {
//        return val >= min && val <= max;
        return val.compareTo(min) < 0 || val.compareTo(max) > 0;
    }
}
