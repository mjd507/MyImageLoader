package com.fighting.myimageloader.cache;

import android.content.Context;
import android.graphics.Bitmap;

import com.fighting.myimageloader.request.BitmapRequest;

/**
 * 描述：
 * Created by MaJD on 2016/8/4.
 */

public class DoubleCache implements BitmapCache {

    DiskCache mDiskCache;
    MemoryCache mMemoryCache = new MemoryCache();

    public DoubleCache(Context context) {
        mDiskCache = DiskCache.getInstance(context);
    }

    @Override
    public Bitmap get(BitmapRequest key) {
        Bitmap bitmap = mMemoryCache.get(key);
        if (bitmap == null) {
            bitmap = mDiskCache.get(key);
            saveBitMapToMemory(key, bitmap);
        }
        return null;
    }

    private void saveBitMapToMemory(BitmapRequest key, Bitmap bitmap) {
        if (bitmap != null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    @Override
    public void put(BitmapRequest key, Bitmap bitmap) {
        mMemoryCache.put(key, bitmap);
        mDiskCache.put(key, bitmap);
    }

    @Override
    public void remove(BitmapRequest key) {
        mMemoryCache.remove(key);
        mDiskCache.remove(key);
    }
}
