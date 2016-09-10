package net.juude.rxdemos;
import junit.framework.Assert;

import net.juude.rxdemos.test.SimplePrintSubscriber;

import org.junit.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.observers.TestSubscriber;
import top.perf.utils.LogUtils;

/**
 * Created by juude on 16/8/24.
 */
public class ErrorHandlingTest {

    @Test
    public void testRetry() {
        final AtomicInteger atomicInteger = new AtomicInteger(0);
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext(String.valueOf(System.currentTimeMillis()));
                subscriber.onError(new Error("error"));
            }
        })
        .doOnSubscribe(new Action0() {
            @Override
            public void call() {
                atomicInteger.incrementAndGet();
            }
        })
        .retry(2)
        .toBlocking()
        .subscribe(new TestSubscriber<String>());
        Assert.assertTrue(atomicInteger.intValue() == 3);
    }

    @Test
    public void testRetryFunc() {
        final AtomicInteger atomicInteger = new AtomicInteger(0);
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext(String.valueOf(System.currentTimeMillis()));
                subscriber.onError(new Exception(String.valueOf(atomicInteger.incrementAndGet())));
            }
        })
        .retry(new Func2<Integer, Throwable, Boolean>() {
            @Override
            public Boolean call(Integer integer, Throwable throwable) {
                System.out.println("integer: " + integer + "  throwable : " + throwable.getMessage() + "\n" + LogUtils.getStackTraceString(throwable));
                return false;
            }
        })
        .toBlocking()
        .subscribe(new SimplePrintSubscriber("testRetryFunc"));
        System.out.println("atomicInteger : " + atomicInteger.intValue());
        Assert.assertTrue(atomicInteger.intValue() == 5);
    }

    @Test
    public void testRetryWhen() {
        final AtomicInteger atomicInteger = new AtomicInteger(3);
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext(String.valueOf(System.currentTimeMillis()));
                subscriber.onError(new Error(String.valueOf(atomicInteger.decrementAndGet())));
            }
        })
        .retryWhen(new Func1<Observable<? extends Throwable>, Observable<?>>() {
            @Override
            public Observable<?> call(Observable<? extends Throwable> observable) {
                return observable.takeWhile(new Func1<Throwable, Boolean>() {
                    @Override
                    public Boolean call(Throwable throwable) {
                        return Integer.parseInt(throwable.getMessage()) > 0;
                    }
                })
                .flatMap(new Func1<Throwable, Observable<?>>() {
                    @Override
                    public Observable<?> call(Throwable throwable) {
                        return Observable.timer(1, TimeUnit.SECONDS);
                    }
                });
            }
        })
        .toBlocking()
        .subscribe(new TestSubscriber<String>());
        Assert.assertEquals(atomicInteger.intValue(), 0);
    }

    @Test
    public void testOnErrorReturn() {
        final AtomicInteger atomicInteger = new AtomicInteger(0);
        TestSubscriber<Integer> testSubscriber = new TestSubscriber<>();
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onError(new Exception(String.valueOf(atomicInteger.getAndIncrement())));
            }
        })
        .onErrorReturn(new Func1<Throwable, Integer>() {
            @Override
            public Integer call(Throwable throwable) {
                return -1;
            }
        })
        .subscribe(testSubscriber);
        Assert.assertEquals(testSubscriber.getOnNextEvents().get(0).intValue(), -1);
    }

    @Test
    public void testOnExceptionResumeNext() {
        final AtomicInteger atomicInteger = new AtomicInteger(0);
        TestSubscriber<Integer> testSubscriber = new TestSubscriber<>();
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onNext(-1);
                subscriber.onError(new Exception(String.valueOf(atomicInteger.getAndIncrement())));
            }
        })
        .onErrorResumeNext(Observable.just(1,3,5))
        .subscribe(testSubscriber);
        List<Integer> nextList = testSubscriber.getOnNextEvents();
        Assert.assertTrue(nextList.size() == 4);
        Assert.assertTrue(nextList.get(0) == -1);
        Assert.assertTrue(nextList.get(1) == 1);
        Assert.assertTrue(nextList.get(2) == 3);
        Assert.assertTrue(nextList.get(3) == 5);
    }
}
