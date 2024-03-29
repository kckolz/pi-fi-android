package pack.wolf.com.pifi.network;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

public class BitmapLRUCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache {

    public BitmapLRUCache () {

        this (getDefaultLruCacheSize ());
    }

    public BitmapLRUCache (int sizeInKiloBytes) {

        super (sizeInKiloBytes);
    }

    public static int getDefaultLruCacheSize () {

        final int maxMemory = (int) (Runtime.getRuntime ().maxMemory () / 1024);
        final int cacheSize = maxMemory / 8;

        return cacheSize;
    }

    @Override
    protected int sizeOf (String key, Bitmap value) {

        return value.getRowBytes () * value.getHeight () / 1024;
    }

    @Override
    public Bitmap getBitmap (String url) {

        return get (url);
    }

    @Override
    public void putBitmap (String url, Bitmap bitmap) {

        put (url, bitmap);
    }
}