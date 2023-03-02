package com.programm.ge.saphire2d.ui.subscription;

public interface Subscribable<T> {

    Subscription subscribe(T listener);

}
