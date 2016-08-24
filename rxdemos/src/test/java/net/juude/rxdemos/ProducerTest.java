package net.juude.rxdemos;

import net.juude.rxdemos.test.SimplePrintSubscriber;

import org.junit.Test;

import rx.Observable;
import rx.Producer;
import rx.Subscriber;
import rx.observers.TestSubscriber;

/**
 * Created by sjd on 16/8/12.
 * TODO:// request not only onSubscribe
 */
public class ProducerTest {
    @Test
    public void testProducer() {
        SimplePrintSubscriber simplePrintSubscriber = new SimplePrintSubscriber("producertest");
        Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(final Subscriber<? super Object> subscriber) {
                Producer producer = new Producer() {
                    @Override
                    public void request(long n) {
                        System.out.println("request : " + n);
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
}
