package net.juude.rxdemos;

import net.juude.rxdemos.test.SimplePrintSubscriber;

import org.junit.Test;

import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Producer;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func2;
import rx.observers.TestSubscriber;
import rx.subjects.PublishSubject;
import top.perf.utils.timer.TimerUtils;

/**
 * Created by sjd on 16/8/12.
 * TODO:// request not only onSubscribe
 */
public class ProducerTest {
    /**
     * produce only one no matter how much time requested.
     */
    @Test
    public void testProducer() {
        SimplePrintSubscriber simplePrintSubscriber = new SimplePrintSubscriber("producertest");
        Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(final Subscriber<? super Object> subscriber) {
                Producer producer = new Producer() {
                    @Override
                    public void request(long n) {
                        System.out.println("request : ");
                        subscriber.onNext(3);
                    }
                };
                subscriber.setProducer(producer);
            }
        })
        .take(3)
        .subscribe(simplePrintSubscriber);
    }

    @Test
    public void testSingleProducer() {
        Observable<Integer> source = Observable.just(1);

        TestSubscriber<Integer> ts = new TestSubscriber<>();
        ts.requestMore(0);                                     // (1)

        source.subscribe(ts);

        for (Integer event : ts.getOnNextEvents()) {
            System.out.println(event);
        }
        System.out.println("--");

        ts.unsubscribe();                                      // (2)

        source.subscribe(ts);

        for (Integer event : ts.getOnNextEvents()) {
            System.out.println(event);
        }
    }

    //this will produce a date string every second.
    @Test
    public void testProduceWithTimer() {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.setProducer(new DateProducer(subscriber));
            }
        });
        Observable.zip(Observable.interval(1, TimeUnit.SECONDS), observable, new Func2<Long, String, String>() {
            @Override
            public String call(Long integer, String s) {
                return s;
            }
        })
        .doOnTerminate(new Action0() {
            @Override
            public void call() {
                countDownLatch.countDown();
            }
        })
        .subscribe(new Action1() {
            @Override
            public void call(Object o) {
                System.out.println(o);
            }
        });
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class DateProducer implements Producer{
        private final Subscriber<? super String> subscriber;

        public DateProducer(Subscriber<? super String> subscriber) {
            this.subscriber = subscriber;
        }

        @Override
        public void request(long n) {
            //System.out.println("request: [" + n + "] ismax : " + (n == Long.MAX_VALUE));
            if (n == Long.MAX_VALUE) {
                subscriber.onNext(new Date().toString());
                return;
            }
            subscriber.onNext(new Date().toString());
        }
    }

    @Test
    public void testRequestUpdate() {
        final PublishSubject<Integer> publishSubject = PublishSubject.create();
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {
                final DateProducer dateProducer = new DateProducer(subscriber);
                subscriber.setProducer(dateProducer);
                publishSubject.subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        dateProducer.request(integer);
                    }
                });
            }
        })
        .doOnCompleted(new Action0() {
            @Override
            public void call() {
                System.out.println("doOnCompleted");
            }
        })
        .subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println(s);
            }
        });
        publishSubject.onNext(1);
        publishSubject.onNext(1);
        publishSubject.onNext(1);
        TimerUtils.sleepIgnoreExceptions(1000);
    }
}
