package com.fighting.myimageloader.core;

import android.util.Log;

import com.fighting.myimageloader.loader.Loader;
import com.fighting.myimageloader.loader.LoaderManager;
import com.fighting.myimageloader.request.BitmapRequest;

import java.util.concurrent.BlockingQueue;

/**
 * 描述：
 * Created by MaJD on 2016/8/4.
 */
final class RequestDispatcher extends Thread{

    private BlockingQueue<BitmapRequest> mRequestQueue;

    public RequestDispatcher(BlockingQueue<BitmapRequest> queue) {
        mRequestQueue = queue;
    }

    @Override
    public void run() {
        try {
            while (!this.isInterrupted()) {
                final BitmapRequest request = mRequestQueue.take();
                if (request.isCancel) {
                    continue;
                }

                final String schema = parseSchema(request.imageUri);
                Loader imageLoader = LoaderManager.getInstance().getLoader(schema);
                imageLoader.loadImage(request);
            }
        } catch (InterruptedException e) {
            Log.i("", "### 请求分发器退出");
        }
    }

    private String parseSchema(String uri) {
        if (uri.contains("://")) {
            return uri.split("://")[0];
        } else {
            Log.e(getName(), "### wrong scheme, image uri is : " + uri);
        }

        return "";
    }
}
