package com.programm.saphire2d.ui.subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class SubscriptionManager<T> implements Subscribable<Consumer<T>> {

    private class SubscriptionImpl implements Subscription {
        final Consumer<T> listener;

        public SubscriptionImpl(Consumer<T> listener) {
            this.listener = listener;
        }

        @Override
        public void cancel() {
            for(int i=0;i<subscriptions.size();i++){
                if(subscriptions.get(i) == this){
                    subscriptions.remove(i);
                    return;
                }
            }
        }
    }

    private final List<SubscriptionImpl> subscriptions = new ArrayList<>();

    @Override
    public Subscription subscribe(Consumer<T> listener) {
        SubscriptionImpl subscription = new SubscriptionImpl(listener);
        subscriptions.add(subscription);
        return subscription;
    }

    public void notifyChange(T t){
        for(int i=0;i<subscriptions.size();i++){
            subscriptions.get(i).listener.accept(t);
        }
    }
}
