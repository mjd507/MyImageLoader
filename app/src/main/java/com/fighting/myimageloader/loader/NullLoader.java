package com.fighting.myimageloader.loader;

import android.graphics.Bitmap;

import com.fighting.myimageloader.request.BitmapRequest;

/**
 * 描述：
 * Created by MaJD on 2016/8/4.
 */

public class NullLoader extends AbsLoader{
    @Override
    protected Bitmap onLoadImage(BitmapRequest request) {
        return null;
    }
}
