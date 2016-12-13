package net.juude.concurrent;

import org.junit.Test;

/**
 * Created by sjd on 2016/12/8.
 */

public class NumbersTest {

    @Test
    public void testInteger() {
        System.out.println(Integer.parseInt("342"));
        System.out.println(Integer.parseInt(""));
        System.out.println(Integer.parseInt("0x342232"));
    }

    @Test
    public void testDouble() {
        System.out.println(Double.parseDouble("0.3"));
        System.out.println(Double.parseDouble("3233.3"));
        System.out.println(Double.parseDouble(""));
    }

}
