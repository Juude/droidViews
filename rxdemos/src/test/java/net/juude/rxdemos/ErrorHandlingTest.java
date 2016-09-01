package net.juude.rxdemos;

import android.provider.Settings;
import android.util.Log;

import net.juude.rxdemos.test.SimplePrintSubscriber;

import org.junit.Test;

import java.util.Observer;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.subjects.PublishSubject;
import top.perf.utils.LogUtils;
import top.perf.utils.timer.TimerUtils;

/**
 * Created by juude on 16/8/24.
 */
public class ErrorHandlingTest {

    public static final String TAG = "ErrorHandlingTest";


    //@Test
    public void testRetrSubject() {
        PublishSubject<Integer> publishSubject = PublishSubject.create();
        publishSubject
                .asObservable()
                .retry()
                .subscribe(new SimplePrintSubscriber());
        publishSubject.onNext(100);
        publishSubject.onError(new Exception("dd"));
        publishSubject.onNext(233);
    }

    public void testRetryWhexn() {
        //        Observable.create((new Subscriber<? super String> s) -> {
//            System.out.println("subscribing");
//            s.onError(new RuntimeException("always fails"));
//        }).retryWhen(attempts -> {
//            return attempts.zipWith(Observable.range(1, 3), (n, i) -> i).flatMap(i -> {
//                System.out.println("delay retry by " + i + " second(s)");
//                return Observable.timer(i, TimeUnit.SECONDS);
//            });
//        }).toBlocking().forEach(System.out::println);

    }

    //@Test
    public void testRetry() {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext(String.valueOf(System.currentTimeMillis()));
                subscriber.onError(new Error("ddd"));
            }
        })
        .retry()
        .subscribe(new Subscriber<String>() {

             @Override
             public void onCompleted() {
                 System.out.println("complete");
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
                return observable.zipWith(Observable.range(1, 1000), new Func2<Throwable, Integer, Integer>() {
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
        TimerUtils.sleepIgnoreExceptions(10000);
    }
}
