package net.juude.rxdemos;
import org.junit.Test;
import java.util.List;
import rx.Observable;
import rx.observers.TestSubscriber;
import static org.junit.Assert.assertEquals;
/**
 * Created by juude on 16/9/4.
 */
public class ConcatTest {

    @Test
    public void testConcatEmpty() {
        TestSubscriber<Integer> testSubscriber = new TestSubscriber<>();
        Observable
            .just(1)
            .concatWith(Observable.just(3))
            .take(1)
            .subscribe(testSubscriber);
        List<Integer> nextEvents = testSubscriber.getOnNextEvents();
        assertEquals(nextEvents.size(), 1);
        assertEquals(nextEvents.get(0).intValue(), 1);
    }

}
