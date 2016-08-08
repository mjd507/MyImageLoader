package com.fighting.myimageloader.cache;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.fighting.myimageloader.request.BitmapRequest;

/**
 * 描述：
 * Created by MaJD on 2016/8/4.
 */

public class MemoryCache implements BitmapCache {

    private LruCache<String, Bitmap> mMemoryChche;

    public MemoryCache() {
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        int cacheSize = maxMemory / 4;
        mMemoryChche = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight() / 1024;
            }
        };
    }

    @Override
    public Bitmap get(BitmapRequest request) {
        return mMemoryChche.get(request.imageUri);
    }

    @Override
    public void put(BitmapRequest key, Bitmap bitmap) {
        mMemoryChche.put(key.imageUri, bitmap);
    }

    @Override
    public void remove(BitmapRequest key) {
        mMemoryChche.remove(key.imageUri);
    }
}
