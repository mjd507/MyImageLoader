package com.fighting.myimageloader.loader;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述：
 * Created by MaJD on 2016/8/4.
 */

public class LoaderManager {

    private static LoaderManager mLoaderManager;
    private static final String HTTP = "http";
    private static final String HTTPS = "https";
    private static final String FILE = "file";
    private Map<String, Loader> mLoaderMap = new HashMap<String, Loader>();
    private LoaderManager() {
        mLoaderMap.put(HTTP,new UrlLoader());
        mLoaderMap.put(HTTPS,new UrlLoader());
        mLoaderMap.put(FILE,new LocalLoader());
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

    public Loader getLoader(String schema) {
        if(mLoaderMap.containsKey(schema)){
            return mLoaderMap.get(schema);
        }else {
            return new NullLoader();
        }
    }
}
