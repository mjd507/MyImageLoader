package com.fighting.myimageloader.policy;

import com.fighting.myimageloader.request.BitmapRequest;

/**
 * 描述：
 * Created by MaJD on 2016/8/4.
 */

public class ReversePolicy implements LoadPolicy {
    @Override
    public int compare(BitmapRequest request1, BitmapRequest request2) {
        return 0;
    }
}
