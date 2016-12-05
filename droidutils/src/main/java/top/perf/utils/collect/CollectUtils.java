package top.perf.utils.collect;

import java.util.Collection;

/**
 * Created by sjd on 2016/12/5.
 */

public class CollectUtils {

    public static int sizeOf(Collection collection) {
        if (collection == null) {
            return 0;
        } else {
            return collection.size();
        }
    }
}