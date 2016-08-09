package net.juude.rxdemos;

import net.juude.rxdemos.test.SimplePrintSubscriber;
import net.juude.rxdemos.test.UpdateableOnSubscribe;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.subjects.PublishSubject;

/**
 * Created by juude on 16/8/9.
 */

public class TestMultipleObservers {

    @Test
    public void testMultipleObservers() {
        final UpdateableOnSubscribe updateableOnSubscribe = new UpdateableOnSubscribe();
        final PublishSubject observable = PublishSubject.create();
        Subscription subscription1 = observable.subscribe(new Action1() {
            @Override
            public void call(Object o) {
                System.out.println("subscription1 : " + o);
            }
        });

        subscription1.unsubscribe();
        observable.subscribe(new Action1() {
            @Override
            public void call(Object o) {
                System.out.println("subscription2 : " + o);
            }
        });

        Observable.interval(1, TimeUnit.SECONDS).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                observable.onNext("next  : " + System.currentTimeMillis());
            }
        });
        try {
            Thread.sleep(33333);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPublishSubject() {
        PublishSubject<Object> subject = PublishSubject.create();
        // observer1 will receive all onNext and onCompleted events
        subject.subscribe(new SimplePrintSubscriber());
        subject.onNext("one");
        subject.onNext("two");
        // observer2 will only receive "three" and onCompleted
        subject.subscribe(new SimplePrintSubscriber());
        subject.onNext("three");
        subject.onCompleted();
    }
}
