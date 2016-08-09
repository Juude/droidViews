package net.juude.rxdemos.test;

import rx.Observable;

/**
 * Created by juude on 16/8/9.
 * make a observerable that can receive a event then emits the event
 */
public class UpdateableObserverable extends Observable{

    /**
     * Creates an Observable with a Function to execute when it is subscribed to.
     * <p>
     * <em>Note:</em> Use {@link #create(OnSubscribe)} to create an Observable, instead of this constructor,
     * unless you specifically have a need for inheritance.
     *
     * @param f {@link OnSubscribe} to be executed when {@link #subscribe(Subscriber)} is called
     */
    protected UpdateableObserverable(OnSubscribe f) {
        super(f);
    }
}
