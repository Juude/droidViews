package net.juude.droidviews.otto;

/**
 * Created by juude on 15-7-9.
 * copied from https://github.com/square/otto/blob/master/otto-sample/src/main/java/com/squareup/otto/sample/BusProvider.java
 */

import com.squareup.otto.Bus;

/**
 * Maintains a singleton instance for obtaining the bus. Ideally this would be replaced with a more efficient means
 * such as through injection directly into interested classes.
 */
public final class BusProvider {
    private static final Bus BUS = new Bus();

    public static Bus getInstance() {
        return BUS;
    }

    private BusProvider() {
        // No instances.
    }
}