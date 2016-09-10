package net.juude.concurrent;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * Created by sjd on 16/9/9.
 */

public class CountDownLatchTest {
    @Test
    public void testCountDown() {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        try {
            System.out.println("begin wait");
            Thread.sleep(5000);
            countDownLatch.countDown();
            System.out.println("end wait");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            countDownLatch.await();
            System.out.println("end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
