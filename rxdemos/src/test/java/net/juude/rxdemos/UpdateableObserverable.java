package net.juude.rxdemos;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.observers.TestSubscriber;
import rx.subjects.PublishSubject;
/**
 * Created by sjd on 16/9/11.
 */
public class UpdateableObserverable {

    @Test
    public void test_updateableObserverable() {
        TestSubscriber<Object> testSubscriber = new TestSubscriber<>();
        final PublishSubject publishSubject  = PublishSubject.create();
        Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(final Subscriber<? super Object> subscriber) {
                subscriber.onNext("7");
                Subscription subscription = publishSubject.subscribe(new Observable.OnSubscribe() {
                    @Override
                    public void call(Object o) {
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onNext("2");
                            subscriber.onCompleted();
                        }
                    }
                });
                subscriber.add(subscription);
            }
        }).subscribe(testSubscriber);
        publishSubject.onNext(testSubscriber);
        testSubscriber.awaitTerminalEvent();
        List<Object> nexts = testSubscriber.getOnNextEvents();
        Observable.from(nexts).forEach(new Action1<Object>() {
            @Override
            public void call(Object o) {
                System.out.println(o);
            }
        });
        Assert.assertEquals(nexts.size(), 2);
        Assert.assertEquals(nexts.get(0), "7");
        Assert.assertEquals(nexts.get(1), "2");
    }
}
