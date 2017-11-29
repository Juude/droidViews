package net.juude.rxdemos.multicast;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

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
        final AtomicInteger subscribeCount = new AtomicInteger(0);
        final AtomicInteger resultCount = new AtomicInteger(0);
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
                        subscribeCount.incrementAndGet();
                    }
                })
                .publish()
                .autoConnect(2);
        observable.subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {
                System.out.println("object : " + o);
                resultCount.incrementAndGet();
            }
        });

        observable.subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {
                System.out.println("object2 : " + o);
                resultCount.incrementAndGet();
            }
        });

        observable.subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {
                System.out.println("object3 : " + o);
                resultCount.incrementAndGet();
            }
        });
        Assert.assertEquals(subscribeCount.intValue(), 1);
        Assert.assertEquals(resultCount.intValue(), 2);

    }

    @Test
    public void testNotAutoConnect() {
        final AtomicInteger atomicInteger = new AtomicInteger(0);
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
                        atomicInteger.incrementAndGet();
                    }
                });
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
        Assert.assertEquals(atomicInteger.intValue(), 3);

    }

    @Test
    public void testAutoConnectReplay() {
        final AtomicInteger subscribeCount = new AtomicInteger(0);
        final AtomicInteger resultCount = new AtomicInteger(0);
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
                        subscribeCount.incrementAndGet();
                    }
                })
                .replay(1, TimeUnit.SECONDS)
                .autoConnect();
        observable.subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {
                System.out.println("object : " + o);
                resultCount.incrementAndGet();
            }
        });

        observable.subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {
                System.out.println("object2: " + o);
                resultCount.incrementAndGet();
            }
        });
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAutoConnectForever() {
        final AtomicInteger subscribeCount = new AtomicInteger(0);
        final AtomicInteger resultCount = new AtomicInteger(0);
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
                        subscribeCount.incrementAndGet();
                    }
                })
                .publish()
                .autoConnect(1000);
        observable.subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {
                System.out.println("object : " + o);
                resultCount.incrementAndGet();
            }
        });

        observable.subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {
                System.out.println("object2 : " + o);
                resultCount.incrementAndGet();
            }
        });

        observable.subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {
                System.out.println("object3 : " + o);
                resultCount.incrementAndGet();
            }
        });
        Assert.assertEquals(subscribeCount.intValue(), 0);
        Assert.assertEquals(resultCount.intValue(), 0);
    }

}
