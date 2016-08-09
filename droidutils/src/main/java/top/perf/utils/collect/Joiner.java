package top.perf.utils.collect;

import android.support.annotation.NonNull;

/**
 * Created by juude on 16/8/9.
 */
public class Joiner {
    private String mSeprator;

    public Joiner(@NonNull String seprator) {
        mSeprator = seprator;
    }

    public String of(@NonNull Object ...args) {
        StringBuilder builder = null;
        for(Object object : args) {
            if(builder == null) {
                builder = new StringBuilder();
            }else {
                builder.append(mSeprator);
            }
            builder.append(args);
        }
        return builder.toString();
    }
}