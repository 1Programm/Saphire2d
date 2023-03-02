package com.programm.ge.saphire2d.reactivevalues.filter;

import com.programm.ge.saphire2d.reactivevalues.Observable;
import com.programm.ge.saphire2d.reactivevalues.ObservableValue;

public abstract class NumberCompareFilter <T extends Comparable<T>> extends NumberFilter<T> {

    public static <T extends Comparable<T>> NumberCompareFilter<T> compareLess(Observable<T> o1, ObservableValue<T> o2) {
        return new NumberCompareFilter<>(o1, o2) {
            @Override
            protected boolean filter(T val, T other) {
                return val.compareTo(other) < 0;
            }
        };
    }

    public static <T extends Comparable<T>> NumberCompareFilter<T> compareLessEqual(Observable<T> o1, ObservableValue<T> o2) {
        return new NumberCompareFilter<>(o1, o2) {
            @Override
            protected boolean filter(T val, T other) {
                return val.compareTo(other) <= 0;
            }
        };
    }

    public static <T extends Comparable<T>> NumberCompareFilter<T> compareGreater(Observable<T> o1, ObservableValue<T> o2) {
        return new NumberCompareFilter<>(o1, o2) {
            @Override
            protected boolean filter(T val, T other) {
                return val.compareTo(other) > 0;
            }
        };
    }

    public static <T extends Comparable<T>> NumberCompareFilter<T> compareGreaterEqual(Observable<T> o1, ObservableValue<T> o2) {
        return new NumberCompareFilter<>(o1, o2) {
            @Override
            protected boolean filter(T val, T other) {
                return val.compareTo(other) > 0;
            }
        };
    }

    public static <T extends Comparable<T>> NumberCompareFilter<T> compareEqual(Observable<T> o1, ObservableValue<T> o2) {
        return new NumberCompareFilter<>(o1, o2) {
            @Override
            protected boolean filter(T val, T other) {
                return val.compareTo(other) == 0;
            }
        };
    }


    private final ObservableValue<T> other;

    public NumberCompareFilter(Observable<T> observable, ObservableValue<T> other) {
        super(observable);
        this.other = other;
    }

    protected abstract boolean filter(T val, T other);

    @Override
    protected boolean filter(T val) {
        return !filter(val, other.get());
    }
}
