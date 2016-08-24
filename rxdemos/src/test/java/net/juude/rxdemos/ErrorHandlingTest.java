package net.juude.rxdemos;

import net.juude.rxdemos.test.SimplePrintSubscriber;

import org.junit.Test;

import rx.Observable;
import rx.functions.Func1;
import rx.subjects.PublishSubject;

/**
 * Created by juude on 16/8/24.
 */
public class ErrorHandlingTest {
    @Test
    public void testRetryWhen() {
        PublishSubject<Integer> publishSubject = PublishSubject.create();
        publishSubject
                .asObservable()
                .retryWhen(new Func1<Observable<? extends Throwable>, Observable<?>>() {
                    @Override
                    public Observable<?> call(Observable<? extends Throwable> observable) {
                        return  null;
                    }
                })
                .subscribe(new SimplePrintSubscriber());
    }
}
