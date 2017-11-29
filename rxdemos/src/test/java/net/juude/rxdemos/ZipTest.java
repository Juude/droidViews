package net.juude.rxdemos;

import net.juude.rxdemos.test.SimplePrintSubscriber;

import org.junit.Test;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func2;

/**
 * Created by juude on 2017/11/10.
 */

public class ZipTest {

    @Test
    public void testZipWithEmpty() {
        Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                subscriber.onNext("232");
            }
        }).zipWith(Observable.just("444"), new Func2<Object, Object, Object>() {
            @Override
            public Object call(Object o, Object o2) {
                return String.valueOf(o) + String.valueOf(o2);
            }
        }).subscribe(new SimplePrintSubscriber("testZip"));
    }
}
