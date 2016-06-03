package net.juude.droidviews.libgdx;

import android.util.Log;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.android.AndroidLiveWallpaperService;
import com.badlogic.gdx.backends.android.AndroidWallpaperListener;

/**
 * Created by juude on 15-9-14.
 */
public class SnowWallpaper extends AndroidLiveWallpaperService{

    public SnowWallpaper() {
        super();
    }

    @Override
    public void onCreateApplication () {
        super.onCreateApplication();

        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

        ApplicationListener listener = new MyLiveWallpaperListener();
        initialize(listener, config);
    }

    // implement AndroidWallpaperListener additionally to ApplicationListener
    // if you want to receive callbacks specific to live wallpapers
    public static class MyLiveWallpaperListener extends SnowEffects implements AndroidWallpaperListener {

        @Override
        public void offsetChange (float xOffset, float yOffset, float xOffsetStep, float yOffsetStep, int xPixelOffset,
                                  int yPixelOffset) {
            Log.i("LiveWallpaper test", "offsetChange(xOffset:" + xOffset + " yOffset:" + yOffset + " xOffsetSteep:" + xOffsetStep + " yOffsetStep:" + yOffsetStep + " xPixelOffset:" + xPixelOffset + " yPixelOffset:" + yPixelOffset + ")");
        }

        @Override
        public void previewStateChange (boolean isPreview) {
            Log.i("LiveWallpaper test", "previewStateChange(isPreview:"+isPreview+")");
        }
    }
}
