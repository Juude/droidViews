package net.juude.rxdemos;

import net.juude.rxdemos.test.SimplePrintSubscriber;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by sjd on 16/9/10.
 */
public class CacheTest {

    @Test
    public void testCache() {
        Observable cacheObserverable = Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                subscriber.onNext(333);
                subscriber.onCompleted();
            }
        })
        .cache();
        cacheObserverable.subscribe(new SimplePrintSubscriber("first"));
        cacheObserverable.subscribe(new SimplePrintSubscriber("second"));
    }

    @Test
    public void testReplay() {
        Observable replayObserverable = Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                subscriber.onNext(333);
                subscriber.onCompleted();
            }
        })
        .replay(1, TimeUnit.SECONDS)
        .autoConnect();
        replayObserverable.subscribe(new SimplePrintSubscriber("first"));
        replayObserverable.subscribe(new SimplePrintSubscriber("second"));
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
