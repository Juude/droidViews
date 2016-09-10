package net.juude.rxdemos;
import org.junit.Test;
import java.util.List;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;
import top.perf.utils.timer.TimerUtils;

import static org.junit.Assert.assertEquals;
/**
 * Created by juude on 16/9/4.
 */
public class ConcatMergeTest {

    @Test
    public void testConcatEmpty() {
        TestSubscriber<Integer> testSubscriber = new TestSubscriber<>();
        Observable
                .just(1)
                .concatWith(Observable.<Integer>empty())
                .take(1)
                .subscribe(testSubscriber);
        List<Integer> nextEvents = testSubscriber.getOnNextEvents();
        assertEquals(nextEvents.size(), 1);
        assertEquals(nextEvents.get(0).intValue(), 1);
    }

    @Test
    public void testConcatDelay() {
        TestSubscriber<Integer> testSubscriber = new TestSubscriber<>();
        Observable
        .concat(
        Observable.just(3).filter(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) {
                return integer > 10;
            }
        })
        ,
        Observable.create(new Observable.OnSubscribe<Integer>() {
                @Override
                public void call(Subscriber<? super Integer> subscriber) {
                    TimerUtils.sleepIgnoreExceptions(1000);
                    subscriber.onNext(100);
                }
            })
        )
        .first()
        .subscribe(testSubscriber);
        List<Integer> nextEvents = testSubscriber.getOnNextEvents();
        assertEquals(nextEvents.size(), 1);
        assertEquals(100, nextEvents.get(0).intValue());
    }

    @Test
    public void testMergeDelay() {
        TestSubscriber<Integer> testSubscriber = new TestSubscriber<>();
        Observable
                .merge(
                        Observable.create(new Observable.OnSubscribe<Integer>() {
                            @Override
                            public void call(Subscriber<? super Integer> subscriber) {
                                TimerUtils.sleepIgnoreExceptions(1000);
                                subscriber.onNext(100);
                                TimerUtils.sleepIgnoreExceptions(1000);
                                subscriber.onNext(200);
                                subscriber.onCompleted();
                            }
                        }).subscribeOn(Schedulers.computation()),
                        Observable.just(3).subscribeOn(Schedulers.computation())
                )
                .toBlocking()
                .subscribe(testSubscriber);
        List<Integer> nextEvents = testSubscriber.getOnNextEvents();
        assertEquals(nextEvents.size(), 3);
        assertEquals(3, nextEvents.get(0).intValue());
        assertEquals(100, nextEvents.get(1).intValue());
        assertEquals(200, nextEvents.get(2).intValue());
    }

    @Test
    public void testMerge() {
        Observable<Integer> odds = Observable.just(1, 3, 5).subscribeOn(Schedulers.io());
        Observable<Integer> evens = Observable.just(2, 4, 6);

        Observable.merge(odds, evens)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onNext(Integer item) {
                        System.out.println("Next: " + item);
                    }

                    @Override
                    public void onError(Throwable error) {
                        System.err.println("Error: " + error.getMessage());
                    }

                    @Override
                    public void onCompleted() {
                        System.out.println("Sequence complete.");
                    }
                });
        TimerUtils.sleepIgnoreExceptions(1000);
    }
}
