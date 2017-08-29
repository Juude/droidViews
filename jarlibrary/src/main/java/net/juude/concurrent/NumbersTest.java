package net.juude.concurrent;

import org.junit.Test;

import java.math.BigDecimal;

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


    @Test
    public void testFloat() {
        System.out.println(Float.parseFloat("1.45"));
        System.out.println(Float.parseFloat("0.3"));
        System.out.println(
                Float.parseFloat("1.45") * 100 - Float.parseFloat("0.3") * 100
        );
        System.out.println(
                new BigDecimal("1.45").subtract(new BigDecimal("0.3"))
        );

        System.out.println(
                new BigDecimal("0.01").compareTo(new BigDecimal("0"))
        );

    }

}
