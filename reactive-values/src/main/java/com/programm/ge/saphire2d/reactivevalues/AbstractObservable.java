package com.programm.ge.saphire2d.reactivevalues;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractObservable<T> implements Observable<T> {

    private final List<ChangeListener<T>> listeners = new ArrayList<>();
    private final List<NotifyListener> notifyListeners = new ArrayList<>();
//    private final List<WeakReference<ChangeListener<T>>> weakListeners = new ArrayList<>();
    private boolean notificationsEnabled = true;

    @Override
    public void listenChange(ChangeListener<T> listener) {
        listeners.add(listener);
    }

    @Override
    public void listen(NotifyListener listener) {
        notifyListeners.add(listener);
    }

    @Override
    public void removeListener(ChangeListener<T> listener) {
        listeners.remove(listener);
//        Iterator<WeakReference<ChangeListener<T>>> it = weakListeners.iterator();
//        while(it.hasNext()){
//            ChangeListener<T> l = it.next().get();
//            if(l == null || l == listener){
//                it.remove();
//            }
//        }
    }

//    @Override
//    public void addWeakListener(ChangeListener<T> listener) {
//        weakListeners.add(new WeakReference<>(listener));
//    }

    protected void notifyChange(T val) {
        if(!notificationsEnabled){
            return;
        }

//        List<ChangeListener<T>> listenersSnapshot = new ArrayList<>(listeners);
//        List<WeakReference<ChangeListener<T>>> weakListenersSnapshot = new ArrayList<>(weakListeners);

        for(ChangeListener<T> listener : listeners){
            listener.onChange(val);
        }

        for(NotifyListener listener : notifyListeners){
            listener.notifyChange();
        }

//        for(WeakReference<ChangeListener<T>> ref : weakListenersSnapshot){
//            ChangeListener<T> listener = ref.get();
//            if(listener != null){
//                listener.onChange(val);
//            }
//        }
//
//        Iterator<WeakReference<ChangeListener<T>>> it = weakListenersSnapshot.iterator();
//        while(it.hasNext()){
//            ChangeListener<T> listener = it.next().get();
//            if(listener == null){
//                it.remove();
//            }
//        }
    }

    protected void setNotificationsEnabled(boolean enabled){
        this.notificationsEnabled = enabled;
    }
}
