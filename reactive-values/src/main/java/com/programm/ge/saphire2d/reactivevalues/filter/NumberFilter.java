package com.programm.ge.saphire2d.reactivevalues.filter;

import com.programm.ge.saphire2d.reactivevalues.Observable;
import com.programm.ge.saphire2d.reactivevalues.core.FilterableNumber;

public abstract class NumberFilter<T extends Comparable<T>> extends Filter<T> implements FilterableNumber<T> {

    public NumberFilter(Observable<T> observable) {
        super(observable);
    }
}
