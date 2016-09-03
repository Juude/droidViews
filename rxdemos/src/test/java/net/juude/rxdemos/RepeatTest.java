package net.juude.rxdemos;

import net.juude.rxdemos.test.SimplePrintSubscriber;
import org.junit.Assert;
import org.junit.Test;
import java.util.List;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.observers.TestSubscriber;

/**
 * Created by juude on 16/9/3.
 */
public class RepeatTest {

    @Test
    public void testRepeat() {
        TestSubscriber<Integer> testSubscriber = new TestSubscriber();
        Observable.just(3)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        System.out.println("doOnSubscribe");
                    }
                })
                .repeat(3)
                .subscribe(testSubscriber);
        testSubscriber.assertNoErrors();
        testSubscriber.assertCompleted();
        List<Integer> nexts = testSubscriber.getOnNextEvents();
        System.out.println(nexts);
        Assert.assertTrue(nexts.size() == 3 && nexts.get(0) == 3 && nexts.get(1) == 3 && nexts.get(2) == 3);
    }

    @Test
    public void testRepeatWhen() {
        System.out.println("testRepeatWhen");
        Observable.just(3)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        System.out.println("doOnSubscribe  testRepeatWhen");
                    }
                })
                .repeatWhen(new Func1<Observable<? extends Void>, Observable<?>>() {
                    @Override
                    public Observable<?> call(Observable<? extends Void> observable) {
                        return observable.zipWith(Observable.just(1L).repeat(), new Func2<Void, Long, Object>() {
                            @Override
                            public Object call(Void aVoid, Long aLong) {
                                return aLong;
                            }
                        }).flatMap(new Func1<Object, Observable<?>>() {
                            @Override
                            public Observable<?> call(Object o) {
                                return Observable.timer(1, TimeUnit.MINUTES);
                            }
                        });
                    }
                })
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        System.out.println("doOnSubscribe");
                    }
                })
                .subscribe(new SimplePrintSubscriber("doOnSubscribe"));
    }
}
