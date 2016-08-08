package com.fighting.myimageloader.cache;

import android.graphics.Bitmap;

import com.fighting.myimageloader.request.BitmapRequest;

/**
 * 描述：
 * Created by MaJD on 2016/8/4.
 */

public class NoCache implements BitmapCache {
    @Override
    public Bitmap get(BitmapRequest request) {
        return null;
    }

    @Override
    public void put(BitmapRequest key, Bitmap bitmap) {

    }

    @Override
    public void remove(BitmapRequest key) {

    }
}
