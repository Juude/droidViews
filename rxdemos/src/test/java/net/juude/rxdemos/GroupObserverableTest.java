package net.juude.rxdemos;

import org.junit.Test;

import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.observables.GroupedObservable;
import rx.observers.TestSubscriber;

/**
 * Created by juude on 16/9/3.
 */
public class GroupObserverableTest {

    @Test
    public void testGroupOververable() {
        TestSubscriber<GroupedObservable<String, Integer>> testGroupSubscriber = new TestSubscriber<>();
        Observable.range(1, 9)
        .groupBy(new Func1<Integer, String>() {
            @Override
            public String call(Integer integer) {
                return integer % 2 == 0  ? "EVEN" : "ODD";
            }
        })
        .toList()
        .forEach(new Action1<List<GroupedObservable<String, Integer>>>() {
            @Override
            public void call(List<GroupedObservable<String, Integer>> groupedObservables) {
                for (GroupedObservable<String, Integer> groupedObservable : groupedObservables) {
                    if ("EVEN".equals(groupedObservable.getKey())) {
                       groupedObservable.toList().forEach(new Action1<List<Integer>>() {
                           @Override
                           public void call(List<Integer> integers) {
                               System.out.println("even : " + integers);
                           }
                       });
                    } else {
                        groupedObservable.take(0);
                    }
                }
            }
        });
    }
}
