package com.programm.ge.saphire2d.ui.subscription;

import java.util.ArrayList;
import java.util.List;

public class RunnableSubscriptionManager implements Subscribable<Runnable> {

    private class SubscriptionImpl implements Subscription {
        final Runnable listener;

        public SubscriptionImpl(Runnable listener) {
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
    public Subscription subscribe(Runnable listener) {
        SubscriptionImpl subscription = new SubscriptionImpl(listener);
        subscriptions.add(subscription);
        return subscription;
    }

    public void notifyChange(){
        for(int i=0;i<subscriptions.size();i++){
            subscriptions.get(i).listener.run();
        }
    }
}
