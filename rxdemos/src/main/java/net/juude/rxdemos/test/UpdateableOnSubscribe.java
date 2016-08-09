package net.juude.rxdemos.test;

import java.util.HashSet;
import java.util.Iterator;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by sjd on 16/8/9.
 */
public class UpdateableOnSubscribe implements Observable.OnSubscribe<String> {

    private HashSet<Subscriber<? super String>> subscriberSet = new HashSet<>();

    @Override
    public void call(Subscriber<? super String> subscriber) {
        this.subscriberSet.add(subscriber);
        subscriber.onNext("hhe");
    }

    public void update() {
        Iterator<Subscriber<? super String>> subscriberIterator = subscriberSet.iterator();
        while (subscriberIterator.hasNext()) {
            Subscriber<? super String> subscriber = subscriberIterator.next();
            if (subscriber != null && !subscriber.isUnsubscribed()) {
                call(subscriber);
            } else {
                subscriberIterator.remove();
            }
        }
    }

    private void publishResult() {
    }
}
