package com.programm.ge.saphire2d.reactivevalues.core;

import com.programm.ge.saphire2d.reactivevalues.Observable;
import com.programm.ge.saphire2d.reactivevalues.ObservableValue;
import com.programm.ge.saphire2d.reactivevalues.filter.NumberCompareFilter;
import com.programm.ge.saphire2d.reactivevalues.filter.NumberFilter;
import com.programm.ge.saphire2d.reactivevalues.filter.NumberInRangeFilter;

public interface FilterableNumber<T extends Comparable<T>> extends Observable<T> {

    default NumberFilter<T> fGreater(ObservableValue<T> v){
        return NumberCompareFilter.compareGreater(this, v);
    }

    default NumberFilter<T> fGreater(T v){
        return NumberCompareFilter.compareGreater(this, new ObjectConst<>(v));
    }

    default NumberFilter<T> fGreaterEqual(ObservableValue<T> v){
        return NumberCompareFilter.compareGreaterEqual(this, v);
    }

    default NumberFilter<T> fGreaterEqual(T v){
        return NumberCompareFilter.compareGreaterEqual(this, new ObjectConst<>(v));
    }

    default NumberFilter<T> fLess(ObservableValue<T> v){
        return NumberCompareFilter.compareLess(this, v);
    }

    default NumberFilter<T> fLess(T v){
        return NumberCompareFilter.compareLess(this, new ObjectConst<>(v));
    }

    default NumberFilter<T> fLessEqual(T v){
        return NumberCompareFilter.compareLessEqual(this, new ObjectConst<>(v));
    }

    default NumberFilter<T> fLessEqual(ObservableValue<T> v){
        return NumberCompareFilter.compareLessEqual(this, v);
    }

    default NumberFilter<T> fEqual(ObservableValue<T> v){
        return NumberCompareFilter.compareEqual(this, v);
    }

    default NumberFilter<T> fEqual(T v){
        return NumberCompareFilter.compareEqual(this, new ObjectConst<>(v));
    }

    default NumberFilter<T> fInRange(T min, T max) {
        return new NumberInRangeFilter<>(this, min, max);
    }

}
