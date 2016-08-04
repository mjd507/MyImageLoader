package com.fighting.myimageloader.loader;

/**
 * 描述：
 * Created by MaJD on 2016/8/4.
 */

public class LoaderManager {

    private static LoaderManager mLoaderManager;

    private LoaderManager() {
    }

    public static LoaderManager getInstance() {
        if (mLoaderManager == null) {
            synchronized (LoaderManager.class) {
                if (mLoaderManager == null) {
                    mLoaderManager = new LoaderManager();
                }
            }
        }
        return mLoaderManager;
    }

    public static Loader getLoader(String schema) {
        switch (schema) {
            case "http":
            case "https":
                return new UrlLoader();
            case "file":
                return new LocalLoader();
            default:
                return new NullLoader();
        }
    }
}
