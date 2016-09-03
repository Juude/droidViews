package net.juude.rxdemos;
import junit.framework.Assert;
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
                subscriber.onError(new Error("ddd"));
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
    public void testRetryWhen() {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext(String.valueOf(System.currentTimeMillis()));
                subscriber.onError(new Error("ddd"));
            }
        })
        .doOnSubscribe(new Action0() {
            @Override
            public void call() {
                System.out.println("who is subscribe create");
            }
        })
        .retryWhen(new Func1<Observable<? extends Throwable>, Observable<?>>() {
            @Override
            public Observable<?> call(Observable<? extends Throwable> observable) {
                return observable.zipWith(Observable.range(1, 3), new Func2<Throwable, Integer, Integer>() {
                    @Override
                    public Integer call(Throwable throwable, Integer integer) {
                        return integer;
                    }
                }).flatMap(new Func1<Integer, Observable<?>>() {
                    @Override
                    public Observable<?> call(Integer integer) {
                        System.out.println("integer: " + integer);
                        return Observable.timer(integer, TimeUnit.SECONDS);
                    }
                });
            }
        })
        .doOnSubscribe(new Action0() {
            @Override
            public void call() {
                System.out.println("who is subscribe me retry when");
            }
        })
        .doOnCompleted(new Action0() {
            @Override
            public void call() {
                System.out.println("doOnComplete");
            }
        })
        .retryWhen(new Func1<Observable<? extends Throwable>, Observable<?>>() {
            @Override
            public Observable<?> call(Observable<? extends Throwable> observable) {
                return observable;
            }
        })
        .toBlocking()
        .subscribe(new Subscriber<String>() {

            @Override
            public void onCompleted() {
                System.out.println("completexx");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError" + LogUtils.getStackTraceString(e));
            }

            @Override
            public void onNext(String s) {
                System.out.println("onNext : " + s);
            }
        });
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
        org.junit.Assert.assertEquals(testSubscriber.getOnNextEvents().get(0).intValue(), -1);
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
