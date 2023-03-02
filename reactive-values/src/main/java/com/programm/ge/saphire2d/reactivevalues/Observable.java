package com.programm.ge.saphire2d.reactivevalues;

public interface Observable {

    void addListener(ChangeListener listener);

    void removeListener(ChangeListener listener);

    void addWeakListener(ChangeListener listener);

}
