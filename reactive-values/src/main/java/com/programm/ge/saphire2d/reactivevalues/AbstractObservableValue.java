package com.programm.ge.saphire2d.reactivevalues;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractObservableValue <T> implements ObservableValue<T> {

    private final List<ChangeListener> listeners = new ArrayList<>();
    private final List<WeakReference<ChangeListener>> weakListeners = new ArrayList<>();
    private boolean notificationsEnabled = true;

    @Override
    public void addListener(ChangeListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(ChangeListener listener) {
        listeners.remove(listener);
        Iterator<WeakReference<ChangeListener>> it = weakListeners.iterator();
        while(it.hasNext()){
            ChangeListener l = it.next().get();
            if(l == null || l == listener){
                it.remove();
            }
        }
    }

    @Override
    public void addWeakListener(ChangeListener listener) {
        weakListeners.add(new WeakReference<>(listener));
    }

    protected void notifyChange() {
        if(!notificationsEnabled){
            return;
        }

        List<ChangeListener> listenersSnapshot = new ArrayList<>(listeners);
        List<WeakReference<ChangeListener>> weakListenersSnapshot = new ArrayList<>(weakListeners);

        for(ChangeListener listener : listenersSnapshot){
            listener.onChange();
        }

        for(WeakReference<ChangeListener> ref : weakListenersSnapshot){
            ChangeListener listener = ref.get();
            if(listener != null){
                listener.onChange();
            }
        }

        Iterator<WeakReference<ChangeListener>> it = weakListenersSnapshot.iterator();
        while(it.hasNext()){
            ChangeListener listener = it.next().get();
            if(listener == null){
                it.remove();
            }
        }
    }

    protected void setNotificationsEnabled(boolean enabled){
        this.notificationsEnabled = enabled;
    }
}
