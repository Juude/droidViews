package net.juude.rxdemos.test;

import rx.Subscriber;

/**
 * Created by sjd on 16/8/9.
 */
public class SimplePrintSubscriber extends Subscriber{
    @Override
    public void onCompleted() {
        System.out.println(" onCompleted : " );
    }

    @Override
    public void onError(Throwable e) {
        System.out.println(" onError : " + e);
    }

    @Override
    public void onNext(Object o) {
        System.out.println(" onNext : " + o);
    }

}
