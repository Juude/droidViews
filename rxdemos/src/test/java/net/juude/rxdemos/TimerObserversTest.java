package net.juude.rxdemos;

import android.util.Log;
import junit.framework.Assert;

import net.juude.rxdemos.test.SimplePrintSubscriber;

import org.junit.Test;
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
    @Test
    public void testTimer() {
        TestSubscriber testSubscriber = new TestSubscriber();
        Observable.interval(1, TimeUnit.SECONDS).subscribe(testSubscriber);
        testSubscriber.assertNoErrors();
        //testSubscriber.to
        Assert.assertTrue(testSubscriber.getOnNextEvents().isEmpty());

        Observable.interval(1, TimeUnit.SECONDS)
                .take(3)
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

        TimerUtils.sleepIgnoreExceptions(10000);
    }
}
