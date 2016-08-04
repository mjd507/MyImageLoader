package com.fighting.myimageloader.core;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.fighting.myimageloader.cache.NoCache;
import com.fighting.myimageloader.config.DisplayConfig;
import com.fighting.myimageloader.config.ImageLoaderConfig;
import com.fighting.myimageloader.policy.SerialPolicy;
import com.fighting.myimageloader.request.BitmapRequest;

/**
 * 描述：
 * Created by MaJD on 2016/8/4.
 */

public class MyImageLoader {

    private static MyImageLoader myImageLoader;
    private ImageLoaderConfig mConfig;
    private RequestQueue mImageQueue;


    private MyImageLoader() {
    }

    public static MyImageLoader getInstance() {
        if (myImageLoader == null) {
            synchronized (MyImageLoader.class) {
                if (myImageLoader == null) {
                    myImageLoader = new MyImageLoader();
                }
            }
        }
        return myImageLoader;
    }

    public void init(ImageLoaderConfig config) {
        if (config == null) {
            throw new RuntimeException("ImageLoaderConfig must not be null");
        }
        if (config.bitmapCache == null) {
            config.bitmapCache = new NoCache();
        }
        if (config.loadPolicy == null) {
            config.loadPolicy = new SerialPolicy();
        }
        mConfig = config;

        mImageQueue = new RequestQueue(mConfig.threadCount);
        mImageQueue.start();

    }

    public void displayImage(ImageView imageView, String url) {
        displayImage(imageView, url, null, null);
    }

    public void displayImage(ImageView imageView, String url, DisplayConfig config, ImageListener listener) {
        BitmapRequest request = new BitmapRequest(imageView, url, config, listener);
        if (request.displayConfig == null) {
            request.displayConfig = mConfig.displayConfig;
        }
        mImageQueue.addRequest(request);
    }

    public ImageLoaderConfig getConfig(){
        return mConfig;
    }

    public void stop(){
        mImageQueue.stop();
    }

    public interface ImageListener {
        void onComplete(ImageView imageView, Bitmap bitmap, String url);
    }

}
