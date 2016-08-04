package com.fighting.myimageloader.config;

import com.fighting.myimageloader.cache.BitmapCache;
import com.fighting.myimageloader.policy.LoadPolicy;

/**
 * 描述：
 * Created by MaJD on 2016/8/4.
 */

public class ImageLoaderConfig {

    /**
     * 图片缓存配置对象
     */
    public BitmapCache bitmapCache = null;

    /**
     * 加载时的设置和加载失败的设置
     */
    public DisplayConfig displayConfig = new DisplayConfig();

    /**
     * 加载策略
     */
    public LoadPolicy loadPolicy = null;

    /**
     * 线程数量
     */
    public int threadCount = 1;


    public ImageLoaderConfig setThreadCount(int count) {
        this.threadCount = Math.max(1, count);
        return this;
    }

    public ImageLoaderConfig setCache(BitmapCache cache) {
        this.bitmapCache = cache;
        return this;
    }

    public ImageLoaderConfig setLoadingPlaceholder(int resId) {
        this.displayConfig.loadingResId = resId;
        return this;
    }

    public ImageLoaderConfig setNotFoundPlaceholder(int resId) {
        this.displayConfig.failedResId = resId;
        return this;
    }

    public ImageLoaderConfig setLoadPolicy(LoadPolicy policy) {
        this.loadPolicy = policy;
        return this;
    }

}
