package net.juude.rxdemos;

import android.util.Log;
import junit.framework.Assert;

import net.juude.rxdemos.test.SimplePrintSubscriber;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;
import top.perf.utils.timer.TimerUtils;

/**
 * Created by juude on 16/8/10.
 */
public class TimerObserversTest {
    //this will test timer emit item every second
    @Test
    public void testTimer() {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        Observable.interval(1, TimeUnit.SECONDS)
                .take(3)
                .doOnTerminate(new Action0() {
                    @Override
                    public void call() {
                        countDownLatch.countDown();
                    }
                })
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        System.out.println("onNext : " + aLong);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        System.out.println("onNext : " + Log.getStackTraceString(throwable));
                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        System.out.println("onComplete : ");
                    }
                });

        Observable.interval(1, TimeUnit.SECONDS)
                .take(5)
                .subscribeOn(Schedulers.io())
                .subscribe(new SimplePrintSubscriber("take5"));
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //this will mock the ve
    @Test
    public void testTimerRequest() {
        Observable.timer(1, TimeUnit.SECONDS).take(3).toBlocking().subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                System.out.println(aLong);
            }
        });
    }
}
