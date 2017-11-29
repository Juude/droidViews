package net.juude.rxdemos.multicast;

import net.juude.rxdemos.test.SimplePrintSubscriber;

import org.junit.Test;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.observables.ConnectableObservable;
import rx.schedulers.Schedulers;
import top.perf.utils.timer.TimerUtils;

/**
 * Created by sjd on 2017/11/7.
 */

public class HotObserverableTest {

    @Test
    public void testHotObserverable() {
        final AtomicInteger atomicInteger = new AtomicInteger(1);
        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {
                Schedulers.computation().createWorker().schedulePeriodically(new Action0() {
                    @Override
                    public void call() {
                        atomicInteger.incrementAndGet();
                        subscriber.onNext(atomicInteger.toString());
                    }
                },0,  1, TimeUnit.SECONDS);
            }
        });
        Subscription subscription = observable.subscribe(new SimplePrintSubscriber("test1"));
        System.out.println("over1");
        TimerUtils.sleepIgnoreExceptions(5000);
        System.out.println("over2");
        subscription.unsubscribe();
        TimerUtils.sleepIgnoreExceptions(999999);
    }

    @Test
    public void testConnectThenSubscribe() {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        ConnectableObservable<Object> observable = Observable
                .create(new Observable.OnSubscribe<Object>() {
                    @Override
                    public void call(final Subscriber<? super Object> subscriber) {
                        Observable.interval(1, 1, TimeUnit.SECONDS)
                                  .subscribe(new Subscriber<Long>() {
                                      @Override
                                      public void onCompleted() {

                                      }

                                      @Override
                                      public void onError(Throwable e) {

                                      }

                                      @Override
                                      public void onNext(Long aLong) {
                                          if (!subscriber.isUnsubscribed()) {
                                              System.out.println("testConnectThenSubscribe OnSubscribe called");
                                          }
                                      }
                                  });
                        subscriber.onNext("fffff");
                        subscriber.onNext("fffff2");
                        subscriber.onNext("fffff3");
                        subscriber.onError(new Exception("error"));
                    }
                })
                .replay(1);
        //observable.connect();
        Subscription a = observable.connect();
        //observable.refCount();
        //observable.connect();
        SimplePrintSubscriber simplePrintSubscriber1 = new SimplePrintSubscriber("testConnectThenSubscribe");
        Subscription subscription1 = observable.subscribe(simplePrintSubscriber1);
        SimplePrintSubscriber simplePrintSubscriber2 = new SimplePrintSubscriber("testConnectThenSubscribe2");
        Subscription subscription2 =  observable.subscribe(simplePrintSubscriber2);
//        simplePrintSubscriber1.unsubscribe();
//        simplePrintSubscriber2.unsubscribe();
        //subscription1.unsubscribe();
        //subscription2.unsubscribe();
        a.unsubscribe();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
