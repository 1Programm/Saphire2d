package com.programm.ge.saphire2d.reactivevalues.expression;

import com.programm.ge.saphire2d.reactivevalues.AbstractObservable;
import com.programm.ge.saphire2d.reactivevalues.ChangeListener;
import com.programm.ge.saphire2d.reactivevalues.ObservableValue;
import lombok.RequiredArgsConstructor;

public abstract class Expression<T> extends AbstractObservable<T> implements ObservableValue<T> {

    @RequiredArgsConstructor
    private class ValueListener implements ChangeListener<Object> {
        private final int index;
        @Override
        public void onChange(Object val) {
            notifyChange(index, val);
        }
    }

    private final Object[] valueArray;
    private T lastValue;

    @SuppressWarnings({"unchecked"})
    protected Expression(ObservableValue<?>... values){
        if(values.length == 0){
            throw new IllegalArgumentException("Can't create an Expression without arguments!");
        }

        valueArray = new Object[values.length];
        for(int i=0;i<values.length;i++){
            valueArray[i] = values[i].get();
        }

        for(int i=0;i<values.length;i++){
            ObservableValue observableValue = values[i];
            observableValue.listenChange(new ValueListener(i));
        }
    }

    private void notifyChange(int i, Object o){
        valueArray[i] = o;
        lastValue = express(valueArray);
        notifyChange(lastValue);
    }

    protected abstract T express(Object[] values);

    @Override
    public final T get() {
        return lastValue;
    }

}
