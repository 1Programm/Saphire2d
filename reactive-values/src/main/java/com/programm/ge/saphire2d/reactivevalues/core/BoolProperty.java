package com.programm.ge.saphire2d.reactivevalues.core;

import com.programm.ge.saphire2d.reactivevalues.AbstractProperty;

public abstract class BoolProperty extends AbstractProperty<Boolean> implements BoolObservable {

    public void invert(){
        set(!get());
    }

}
