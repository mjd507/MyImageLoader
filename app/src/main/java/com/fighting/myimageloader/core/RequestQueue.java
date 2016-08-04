package com.fighting.myimageloader.core;

import android.util.Log;

import com.fighting.myimageloader.request.BitmapRequest;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 描述：请求队列，可按照优先级处理 [线程安全]
 * Created by MaJD on 2016/8/4.
 */

public class RequestQueue {


    private BlockingQueue<BitmapRequest> mRequestQueue = new PriorityBlockingQueue<>();

    private AtomicInteger mSerialNumGenerator = new AtomicInteger(0);

    public static int DEFAULT_CORE_NUMS = Runtime.getRuntime().availableProcessors() + 1;

    private int mDispatchNums = DEFAULT_CORE_NUMS;

    private RequestDispatcher[] mDispatchers = null;

    public RequestQueue() {
        this(DEFAULT_CORE_NUMS);
    }

    public RequestQueue(int coreNums) {
        mDispatchNums = coreNums;
    }

    private void stop() {
        if (mDispatchers != null && mDispatchers.length > 0) {
            for (RequestDispatcher mDispatcher : mDispatchers) {
                mDispatcher.interrupt();
            }
        }
    }

    public void start() {
        stop();
        startDispatchers();
    }

    private void startDispatchers() {
        mDispatchers = new RequestDispatcher[mDispatchNums];
        for (int i = 0; i < mDispatchNums; i++) {
            mDispatchers[i] = new RequestDispatcher(mRequestQueue);
            mDispatchers[i].start();
        }
    }

    public void addRequest(BitmapRequest request) {
        if (!mRequestQueue.contains(request)) {
            request.serialNum = this.generateSerialNum();
            mRequestQueue.add(request);
        } else {
            Log.d("", "### 请求队列中已经含有");
        }
    }

    private int generateSerialNum() {
        return mSerialNumGenerator.incrementAndGet();
    }
}
