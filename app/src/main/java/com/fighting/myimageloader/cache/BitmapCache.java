package com.fighting.myimageloader.cache;

import android.graphics.Bitmap;

import com.fighting.myimageloader.request.BitmapRequest;

/**
 * 描述：
 * Created by MaJD on 2016/8/4.
 */

public interface BitmapCache {
    Bitmap get(BitmapRequest request);
}
