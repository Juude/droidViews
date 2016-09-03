package net.juude.rxdemos;

import org.junit.Test;

import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by juude on 16/9/3.
 */
public class MulticastingTest {
    @Test
    public void testAutoConnect() {
        Observable<Object> observable = Observable.just("Event")
                .map(new Func1<String, Object>() {
                    @Override
                    public Object call(String s) {
                        return "Event2";
                    }
                })
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        System.out.println("doOnSubscribe aa");
                    }
                });
                //.publish()
                //.autoConnect(2);
        observable.subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {
                System.out.println("object : " + o);
            }
        });

        observable.subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {
                System.out.println("object2 : " + o);
            }
        });

        observable.subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {
                System.out.println("object3 : " + o);
            }
        });
    }
}
