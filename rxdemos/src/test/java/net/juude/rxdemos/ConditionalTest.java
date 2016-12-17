package net.juude.rxdemos;
import net.juude.rxdemos.test.SimplePrintSubscriber;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.observers.TestSubscriber;
import top.perf.utils.timer.TimerUtils;

/**
 * Created by juude on 16/8/11.
 */
public class ConditionalTest {

    @Test
    public void testAmb() {
        TestSubscriber testSubscriber = new TestSubscriber();
        Observable
        .interval(1, TimeUnit.SECONDS)
        .map(new Func1<Long, Long>() {
            @Override
            public Long call(Long aLong) {
                return 666L * aLong;
            }
        })
        .ambWith(Observable.interval(2, TimeUnit.SECONDS))
        .subscribe(testSubscriber);
        testSubscriber.assertNoErrors();
        System.out.println(testSubscriber.getOnNextEvents());
        TimerUtils.sleepIgnoreExceptions(5000);
        System.out.println(testSubscriber.getOnNextEvents());
    }

    @Test
    public void testDefaultO() {
        Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                subscriber.onCompleted();
            }
        }).defaultIfEmpty(3)
         .subscribe(new SimplePrintSubscriber("testDefault"));
    }


    @Test
    public void testSwitch() {
        Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                subscriber.onCompleted();
            }
        })
        .switchIfEmpty(Observable.just("333"))
        .subscribe(new SimplePrintSubscriber("testDefault"));
    }
}
