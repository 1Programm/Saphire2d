package com.programm.ge.saphire2d.reactivevalues;

import com.programm.ge.saphire2d.reactivevalues.core.IntObservable;
import com.programm.ge.saphire2d.reactivevalues.core.IntPropertyValue;

public interface Observable<T> {

    void listenChange(ChangeListener<T> listener);

    void listen(NotifyListener listener);


    void removeListener(ChangeListener<T> listener);

//    void addWeakListener(ChangeListener<T> listener);


    default IntObservable countChange(){
        IntPropertyValue counter = new IntPropertyValue();
        listen(counter::increment);
        return counter;
    }

}
