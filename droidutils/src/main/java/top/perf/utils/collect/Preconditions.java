package top.perf.utils.collect;

/**
 * Created by sjd on 16/8/9.
 */
public class Preconditions {
    public static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
        return reference;
    }
}
