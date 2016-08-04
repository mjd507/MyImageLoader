package com.fighting.myimageloader;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.fighting.myimageloader.cache.DoubleCache;
import com.fighting.myimageloader.config.ImageLoaderConfig;
import com.fighting.myimageloader.core.MyImageLoader;
import com.fighting.myimageloader.policy.SerialPolicy;

/**
 * 描述：
 * Created by MaJD on 2016/8/4.
 */

public class Test extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageView imageView = new ImageView(this);
        setContentView(imageView);
        ImageLoaderConfig config = new ImageLoaderConfig()
                .setCache(new DoubleCache())
                .setLoadPolicy(new SerialPolicy())
                .setThreadCount(5);
        MyImageLoader imageLoader = MyImageLoader.getInstance();
        imageLoader.init(config);
        imageLoader.displayImage(imageView, "http://mmbiz.qpic.cn/mmbiz/tnZGrhTk4ddVIcWcI5xxyZzYyHnjMrziawtfYATA3cNIYYYPibhB8doHomfC418mjcAIeaFD27zic3yMEyiafIsbDg/640?wx_fmt=jpeg&tp=webp&wxfrom=5");

    }
}
