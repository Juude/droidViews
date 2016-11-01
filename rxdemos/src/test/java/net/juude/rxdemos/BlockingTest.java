package net.juude.rxdemos;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;

/**
 * Created by sjd on 16/9/9.
 */
public class BlockingTest {

    @Test
    public void testSchedulerlocking() {
        Observable.interval(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .take(3)
                .toBlocking()
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        System.out.println(aLong);
                    }
                });
    }

    @Test
    public void testTimerBlocking() {
        Observable.interval(1, TimeUnit.SECONDS)
                .take(3)
                .toBlocking()
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        System.out.println(aLong);
                    }
                });
    }

    @Test
    public void testBlocking() {
        Observable
                .create(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        subscriber.onNext(1);
                        //subscriber.onCompleted();
                    }
                })
                .take(1)
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.io())
                .toBlocking()
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        System.out.println("blocking");
                    }
                });
    }

    @Test
    public void testBlockingError() {
        TestSubscriber testSubscriber = new TestSubscriber();
        Observable
                .create(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        subscriber.onError(new Exception("no!!"));
                        //subscriber.onCompleted();
                    }
                })
                .take(1)
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.io())
                .toBlocking()
                .subscribe(testSubscriber);
        testSubscriber.assertError(Exception.class);
    }

    @Test
    public void testNoneBlocking() {
        Observable
                .create(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        subscriber.onNext(1);
                    }
                })
                .take(1)
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.io())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        System.out.println("non blocking");
                    }
                });
    }
}
