package net.juude.rxdemos;

import junit.framework.Assert;

import net.juude.rxdemos.test.SimplePrintSubscriber;
import net.juude.rxdemos.test.UpdateableOnSubscribe;

import org.junit.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.observers.TestSubscriber;
import rx.subjects.AsyncSubject;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;
import rx.subjects.ReplaySubject;
/**
 * Created by juude on 16/8/9.
 */

public class SubjectTest {

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

    @Test
    public void testCompleteTwice() {
        PublishSubject<String> publishSubject = PublishSubject.create();
        SimplePrintSubscriber subscriber = new SimplePrintSubscriber("testCompleteTwice");
        publishSubject.retry(2).subscribe(subscriber);
        publishSubject.onNext("ddd");
        publishSubject.onError(new Error());
        System.out.println(subscriber.isUnsubscribed());
        if (subscriber.isUnsubscribed()) {
            publishSubject.subscribe(subscriber);
        }
        publishSubject.onNext("e333");
        publishSubject.onCompleted();
        publishSubject.onCompleted();
    }

    @Test
    public void testRetry() {
        Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {
                System.out.println("subscribing");
                subscriber.onError(new RuntimeException("always fails"));
            }
        }).retryWhen(new Func1<Observable<? extends Throwable>, Observable<?>>() {
            @Override
            public Observable<?> call(Observable<? extends Throwable> observable) {
                return observable.zipWith(Observable.range(1, 3), new Func2<Throwable, Integer, Object>() {
                    @Override
                    public Object call(Throwable throwable, Integer integer) {
                        return integer;
                    }
                }).flatMap(new Func1<Object, Observable<?>>() {
                    @Override
                    public Observable<?> call(Object integer) {
                        System.out.println("delay retry by " + integer + " second(s)");
                        return Observable.timer((Integer)integer, TimeUnit.SECONDS);
                    }
                });
            }
        }).toBlocking().forEach(new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println(s);
            }
        });

    }

    @Test
    public void testBehaviorSubject() {
        BehaviorSubject<String> behaviorSubject = BehaviorSubject.create();
        behaviorSubject.onNext("eefa");
        behaviorSubject.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println(s);
            }
        });
        behaviorSubject.onNext("fsafasdf");
        behaviorSubject.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println("first " + s);
            }
        });
        behaviorSubject.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println("jlkl  " + s);
            }
        });
    }

    @Test
    public void testOnSubscribe() {
        final AtomicBoolean doOnSubscribeCalled = new AtomicBoolean(false);
        final AtomicBoolean doOnTerminateCalled = new AtomicBoolean(false);
        final AtomicBoolean doOnNextCalled = new AtomicBoolean(false);
        PublishSubject<Integer> publishSubject = PublishSubject.create();
        publishSubject
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        doOnSubscribeCalled.set(true);
                    }
                })
                .doOnTerminate(new Action0() {
                    @Override
                    public void call() {
                        doOnTerminateCalled.set(true);
                    }
                })
                .doOnNext(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        doOnNextCalled.set(true);
                    }
                })
                .subscribe(new TestSubscriber<Integer>());
        publishSubject.onNext(22232);
        publishSubject.onError(new Exception("wewewe"));
        Assert.assertEquals(doOnSubscribeCalled.get(), true);
        Assert.assertEquals(doOnNextCalled.get(), true);
        Assert.assertEquals(doOnTerminateCalled.get(), true);
    }
}
