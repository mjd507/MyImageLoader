package com.fighting.myimageloader.request;

import android.widget.ImageView;

import com.fighting.myimageloader.config.DisplayConfig;
import com.fighting.myimageloader.core.MyImageLoader;
import com.fighting.myimageloader.utils.MD5Utils;

import java.lang.ref.WeakReference;

/**
 * 描述：
 * Created by MaJD on 2016/8/4.
 */

public class BitmapRequest {

    public DisplayConfig displayConfig;
    private WeakReference<ImageView> mImageViewRef;
    private MyImageLoader.ImageListener mImageListener;
    public String imageUri;
    private String imageUriMd5;
    public boolean isCancel;
    public int serialNum;
    public boolean justCacheInMem;

    public BitmapRequest(ImageView imageView, String uri, DisplayConfig config, MyImageLoader.ImageListener listener) {
        mImageViewRef = new WeakReference<ImageView>(imageView);
        displayConfig = config;
        mImageListener = listener;
        imageUri = uri;
        imageView.setTag(uri);//imageView与uri绑定
        imageUriMd5 = MD5Utils.toMD5(uri);
    }

    public int getImageViewWidth() {
    return  0;
}

    public int getImageViewHeight() {
        return  0;
    }
}
