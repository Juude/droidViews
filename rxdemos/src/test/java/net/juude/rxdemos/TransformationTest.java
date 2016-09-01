package net.juude.rxdemos;
import org.junit.Test;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by juude on 16/9/1.
 */
public class TransformationTest {
    @Test
    public void testMap() {
        Observable.just(1, 3).map(new Func1<Integer, Integer>() {
            @Override
            public Integer call(Integer integer) {
                return integer * 2;
            }
        }).toBlocking()
         .forEach(new Action1<Integer>() {
             @Override
             public void call(Integer integer) {
                 System.out.println(integer);
             }
         });
    }

    @Test
    public void testFlatMap() {
        System.out.println("flatMap");
        Observable.just(1, 3)
        .flatMap(new Func1<Integer, Observable<Integer>>(){
            @Override
            public Observable<Integer> call(Integer integer) {
                return Observable.just(integer);
            }
        })
        .toBlocking()
        .forEach(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                System.out.println(integer);
            }
        });
    }
}
