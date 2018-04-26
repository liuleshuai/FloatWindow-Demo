package com.example.liukuo.myapplication;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by LiuKuo at 2018/4/24
 */

public class BitmapCache implements ImageLoader.ImageCache {
    private LruCache<String, Bitmap> cache;

    public BitmapCache() {
        int maxSize = 2 * 1024 * 1024;
        cache = new LruCache<String, Bitmap>(maxSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
//                return value.getRowBytes() * value.getHeight();
            }
        };
    }

    @Override
    public Bitmap getBitmap(String key) {
        return cache.get(key);
    }

    @Override
    public void putBitmap(String key, Bitmap bitmap) {
        cache.put(key, bitmap);
    }
}
