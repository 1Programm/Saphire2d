package com.programm.ge.saphire2d.reactivevalues.filter;

import com.programm.ge.saphire2d.reactivevalues.Observable;

public class BoolFilter extends Filter<Boolean> {

    public BoolFilter(Observable<Boolean> observable) {
        super(observable);
    }

    @Override
    protected boolean filter(Boolean val) {
        return !val;
    }
}
