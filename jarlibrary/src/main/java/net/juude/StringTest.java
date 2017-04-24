package net.juude;

import junit.framework.Assert;

import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;

/**
 * Created by juude on 2016/12/9.
 */

public class StringTest {

    @Test
    public void injectExtension() {
        String url = "uweoiruqoer.png";
        int index = url.lastIndexOf('.');
        int width = 200;
        int height = 200;
        String newUrl = url.substring(0, index) + "." + width + "x" + height + url.substring(index);
        Assert.assertEquals("uweoiruqoer.200x200.png", newUrl);
    }

    @Test
    public void testGetSize() {
        System.out.println("1234".getBytes().length);
        System.out.println("1234无".getBytes().length);
        System.out.println("1234无六七".getBytes().length);
        String str = "1234五六七";
        for (int i = 0; i < str.length(); i++) {
            Character ch = str.charAt(i);
            System.out.println("  " + str.charAt(i));
        }
    }

    @Test
    public void split() {
        System.out.print(Arrays.toString("fdsafdsaf".split(".")));
        System.out.print(Arrays.toString("fdsaf.dsaf".split("\\.")));
    }

    /*
    *
    *
    * */
    @Test
    public void testEncode() {
        String encoded = "http://baidu.com?k=" + URLEncoder.encode("你好");
        System.out.println(encoded);
        try {
            URL url = new URL(encoded);
            System.out.println(url.getQuery());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
