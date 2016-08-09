package net.juude.rxdemos;

import junit.framework.Assert;

import net.juude.rxdemos.test.SimplePrintSubscriber;
import net.juude.rxdemos.test.UpdateableOnSubscribe;

import org.junit.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.observers.TestSubscriber;
import rx.subjects.AsyncSubject;
import rx.subjects.PublishSubject;
import rx.subjects.ReplaySubject;

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
//        try {
//            Thread.sleep(33333);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
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

    @Test
    public void testAsyncSubject() {
        AsyncSubject<Object> subject = AsyncSubject.create();
        subject.subscribe(new SimplePrintSubscriber("1"));
        subject.onNext("one");
        subject.onNext("two");
        subject.onNext("three");

        // observer will receive "three" as the only onNext event.
        AsyncSubject<Object> subject2 = AsyncSubject.create();
        subject2.subscribe(new SimplePrintSubscriber("2"));
        subject2.onNext("one");
        subject2.onNext("two");
        subject2.onNext("three");
        subject2.onCompleted();
    }

    @Test
    public void testReplaySubject() {
        ReplaySubject<String> replaySubject = ReplaySubject.create();
        replaySubject.onNext("22");
        replaySubject.onNext("33");
        replaySubject.subscribe(new SimplePrintSubscriber("replaySubject"));
        replaySubject.onNext("3333");
        replaySubject.onCompleted();
        TestSubscriber<String> testSubscriber = new TestSubscriber<>();
        replaySubject.subscribe(testSubscriber);
        testSubscriber.assertNoErrors();
        List<String> results = testSubscriber.getOnNextEvents();
        Assert.assertTrue(results.size() == 3);
    }
}
