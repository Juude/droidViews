package net.juude.droidviews;

import android.support.annotation.NonNull;

/**
 * Created by juude on 15-5-11.
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
            builder.append(object);
        }
        return builder.toString();
    }
}
