package net.juude.rxdemos.test;

import rx.Subscriber;

/**
 * Created by sjd on 16/8/9.
 */
public class SimplePrintSubscriber extends Subscriber{

    private String name = "noname";

    public SimplePrintSubscriber() {

    }

    public SimplePrintSubscriber(String name) {
        this.name = name;
    }

    @Override
    public void onCompleted() {
        System.out.println(this.name + " onCompleted : " );
    }

    @Override
    public void onError(Throwable e) {
        System.out.println(this.name + " onError : " + e);
    }

    @Override
    public void onNext(Object o) {
        System.out.println(this.name + " onNext : " + o);
    }

}
