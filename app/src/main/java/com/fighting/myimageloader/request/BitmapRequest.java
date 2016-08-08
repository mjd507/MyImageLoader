package com.fighting.myimageloader.request;

import android.widget.ImageView;

import com.fighting.myimageloader.config.DisplayConfig;
import com.fighting.myimageloader.core.MyImageLoader;
import com.fighting.myimageloader.policy.LoadPolicy;
import com.fighting.myimageloader.utils.ImageViewHelper;
import com.fighting.myimageloader.utils.MD5Utils;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Comparator;

/**
 * 描述：
 * Created by MaJD on 2016/8/4.
 */

public class BitmapRequest implements Comparable<BitmapRequest> {
    public int serialNum;
    public DisplayConfig displayConfig;
    private Reference<ImageView> mImageViewRef;
    private MyImageLoader.ImageListener mImageListener;
    public String imageUri;
    private String imageUriMd5;
    public boolean isCancel;
    public boolean justCacheInMem;
    LoadPolicy mLoadPolicy = MyImageLoader.getInstance().getConfig().loadPolicy;

    public BitmapRequest(ImageView imageView, String uri, DisplayConfig config, MyImageLoader.ImageListener listener) {
        mImageViewRef = new WeakReference<ImageView>(imageView);
        displayConfig = config;
        mImageListener = listener;
        imageUri = uri;
        imageView.setTag(uri);//imageView与uri绑定
        imageUriMd5 = MD5Utils.toMD5(uri);

    }

    public void setLoadPolicy(LoadPolicy policy){
        mLoadPolicy = policy;
    }

    public int getImageViewWidth() {
        return ImageViewHelper.getImageViewWidth(mImageViewRef.get());
    }

    public int getImageViewHeight() {
        return ImageViewHelper.getImageViewHeight(mImageViewRef.get());
    }

    @Override
    public int compareTo(BitmapRequest another) {
        return mLoadPolicy.compare(this, another);
    }
}
