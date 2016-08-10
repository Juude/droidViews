package top.perf.utils.timer;

/**
 * Created by sjd on 16/8/10.
 */
public class TimerUtils {
    public static void sleepIgnoreExceptions(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            //e.printStackTrace();
        }
    }
}
