package com.fighting.myimageloader.policy;

import com.fighting.myimageloader.request.BitmapRequest;

/**
 * 描述：
 * Created by MaJD on 2016/8/4.
 */

public interface LoadPolicy {
    int compare(BitmapRequest request1, BitmapRequest request2);
}
