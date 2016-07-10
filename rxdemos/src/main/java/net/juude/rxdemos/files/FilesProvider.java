package net.juude.rxdemos.files;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by sjd on 16/7/10.
 * modified based on http://gank.io/post/560e15be2dca930e00da1083
 */
public class FilesProvider {
    private Observable<Bitmap> getAllFiles(File[] folders) {
        return Observable.from(folders)
                .flatMap(new Func1<File, Observable<File>>() {
                    @Override
                    public Observable<File> call(File file) {
                        return Observable.from(file.listFiles());
                    }
                })
                .filter(new Func1<File, Boolean>() {
                    @Override
                    public Boolean call(File file) {
                        return file.getName().endsWith(".png");
                    }
                })
                .map(new Func1<File, Bitmap>() {
                    @Override
                    public Bitmap call(File file) {
                        return getBitmapFromFile(file);
                    }

                    private Bitmap getBitmapFromFile(File file) {
                        return BitmapFactory.decodeFile(file.getAbsolutePath());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

}
