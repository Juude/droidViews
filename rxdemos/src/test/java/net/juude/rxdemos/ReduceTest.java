package net.juude.rxdemos;

import junit.framework.Assert;

import org.junit.Test;

import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.observers.TestSubscriber;

/**
 * Created by juude on 2016/12/15.
 */

public class ReduceTest {
    @Test
    public void testReduce() {
        TestSubscriber<String> testSubscriber = new TestSubscriber<>();
        Observable
                .just(new String[] {"1", "2", "3"})
                .flatMap(new Func1<String[], Observable<String>>() {
                    @Override
                    public Observable<String> call(String[] strings) {
                        return Observable.from(strings);
                    }
                })
                .reduce("count", new Func2<String, String, String>() {
                    @Override
                    public String call(String s, String s2) {
                        return s + s2;
                    }
                })
                .subscribe(testSubscriber);
        String first = testSubscriber.getOnNextEvents().get(0);
        System.out.println("first" + first);
        Assert.assertEquals("count123", first);
    }
}
