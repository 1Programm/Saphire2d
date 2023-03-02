package com.programm.ge.saphire2d.reactivevalues.filter;

import com.programm.ge.saphire2d.reactivevalues.AbstractObservable;
import com.programm.ge.saphire2d.reactivevalues.Observable;

public abstract class Filter<T> extends AbstractObservable<T> implements Observable<T> {

    public Filter(Observable<T> observable){
        observable.listenChange(this::onChange);
    }

    protected abstract boolean filter(T val);

    private void onChange(T val){
        if(filter(val)) return;
        notifyChange(val);
    }

}
