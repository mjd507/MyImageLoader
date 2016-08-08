package com.fighting.myimageloader.loader;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.fighting.myimageloader.cache.BitmapCache;
import com.fighting.myimageloader.config.DisplayConfig;
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

    protected void showLoading(final BitmapRequest request) {
        final ImageView imageView = request.getImageView();
        if (request.isImageViewTagValid()
                && hasLoadingPlaceholder(request.displayConfig)) {
            imageView.post(new Runnable() {

                @Override
                public void run() {
                    imageView.setImageResource(request.displayConfig.loadingResId);
                }
            });
        }
    }

    protected abstract Bitmap onLoadImage(BitmapRequest request);

    private void cacheBitmap(BitmapRequest request, Bitmap bitmap) {
        if (bitmap != null && mCache != null) {
            synchronized (mCache) {
                mCache.put(request, bitmap);
            }
        }
    }

    protected void deliverToUIThread(final BitmapRequest request, final Bitmap bitmap) {
        final ImageView imageView = request.getImageView();
        if (imageView == null) {
            return;
        }
        imageView.post(new Runnable() {

            @Override
            public void run() {
                updateImageView(request, bitmap);
            }
        });
    }

    private void updateImageView(BitmapRequest request, Bitmap bitmap) {
        final ImageView imageView = request.getImageView();
        final String uri = request.imageUri;
        if (bitmap != null && imageView.getTag().equals(uri)) {
            imageView.setImageBitmap(bitmap);
        }

        // 加载失败
        if (bitmap == null && hasFaildPlaceholder(request.displayConfig)) {
            imageView.setImageResource(request.displayConfig.failedResId);
        }

        // 回调接口
        if (request.mImageListener != null) {
            request.mImageListener.onComplete(imageView, bitmap, uri);
        }
    }

    private boolean hasLoadingPlaceholder(DisplayConfig displayConfig) {
        return displayConfig != null && displayConfig.loadingResId > 0;
    }

    private boolean hasFaildPlaceholder(DisplayConfig displayConfig) {
        return displayConfig != null && displayConfig.failedResId > 0;
    }

}
