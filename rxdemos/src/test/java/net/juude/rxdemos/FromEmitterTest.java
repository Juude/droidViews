package net.juude.rxdemos;


import net.juude.rxdemos.test.SimplePrintSubscriber;

import org.junit.Test;

import java.util.concurrent.Callable;

import rx.AsyncEmitter;
import rx.Observable;
import rx.functions.Action1;
import top.perf.utils.timer.TimerUtils;

/**
 * Created by juude on 2017/11/2.
 */

public class FromEmitterTest {


    @Test
    public void testFromCallable() {
        Observable<Object> objectObservable = Observable.fromCallable(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                throw new RuntimeException("sss" + System.currentTimeMillis());
            }
        }).retry(2);
        objectObservable.subscribe(new SimplePrintSubscriber("testFromEmitter1"));
        TimerUtils.sleepIgnoreExceptions(2000);
        objectObservable.subscribe(new SimplePrintSubscriber("testFromEmitter2"));
    }

    @Test
    public void testFromEmitter() {
        Action1<AsyncEmitter<String>> emitterAction1 = new Action1<AsyncEmitter<String>>() {
            @Override
            public void call(AsyncEmitter<String> stringAsyncEmitter) {
                System.out.println("OKK" + System.currentTimeMillis());
            }
        };
        Observable.fromEmitter(emitterAction1, AsyncEmitter.BackpressureMode.LATEST).subscribe();
    }
}
