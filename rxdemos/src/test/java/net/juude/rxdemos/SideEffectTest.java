package net.juude.rxdemos;


import net.juude.rxdemos.test.SimplePrintSubscriber;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by sjd on 16/9/10.
 */
public class SideEffectTest {

    @Test
    public void testOnSubscribe() {
        final AtomicBoolean doOnSubscribeCalled = new AtomicBoolean(false);
        Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                System.out.println("OnSubscribe before");
                subscriber.onNext(666);
                System.out.println("OnSubscribe after");
            }
        })
        .doOnSubscribe(new Action0() {
            @Override
            public void call() {
                doOnSubscribeCalled.set(true);
                System.out.println("doOnSubscribe");
            }
        })
        .subscribe(new SimplePrintSubscriber("testOnSubscribe"));
    }

    /**
     * called sequence is
     *   OnSubscribe before
         doOnNext:666
         testOnSubscribe onNext : 666
         OnSubscribe after
     * */
    @Test
    public void testOnNext() {
        Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                System.out.println("OnSubscribe before");
                subscriber.onNext(666);
                System.out.println("OnSubscribe after");
            }
        })
        .doOnNext(new Action1<Object>() {
            @Override
            public void call(Object o) {
                System.out.println("doOnNext:" + o);
            }
        })
        .subscribe(new SimplePrintSubscriber("testOnSubscribe"));
    }
}
