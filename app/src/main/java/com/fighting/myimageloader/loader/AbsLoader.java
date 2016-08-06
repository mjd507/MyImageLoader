package com.fighting.myimageloader.loader;

import android.graphics.Bitmap;
import android.util.Log;

import com.fighting.myimageloader.cache.BitmapCache;
import com.fighting.myimageloader.core.MyImageLoader;
import com.fighting.myimageloader.request.BitmapRequest;

/**
 * 描述：
 * Created by MaJD on 2016/8/4.
 */

public abstract class AbsLoader implements Loader {

    private static BitmapCache mCache = MyImageLoader.getInstance().getConfig().bitmapCache;

    @Override
    public void loadImage(BitmapRequest request) {
        Bitmap resultBitmap = mCache.get(request);
        Log.e("", "### 是否有缓存 : " + resultBitmap + ", uri = " + request.imageUri);
        if (resultBitmap == null) {
            showLoading(request);
            resultBitmap = onLoadImage(request);
            cacheBitmap(request, resultBitmap);
        } else {
            request.justCacheInMem = true;
        }

        deliverToUIThread(request, resultBitmap);


    }

    protected void showLoading(BitmapRequest request) {

    }

    protected abstract Bitmap onLoadImage(BitmapRequest request);

    protected void cacheBitmap(BitmapRequest request, Bitmap bitmap) {

    }

    protected void deliverToUIThread(BitmapRequest request, Bitmap resultBitmap) {

    }


}
