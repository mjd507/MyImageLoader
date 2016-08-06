package com.fighting.myimageloader.loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.fighting.myimageloader.request.BitmapRequest;
import com.fighting.myimageloader.utils.BitmapDecoder;
import com.jakewharton.disklrucache.IOUtil;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 描述：
 * Created by MaJD on 2016/8/4.
 */

public class UrlLoader extends AbsLoader {

    @Override
    protected Bitmap onLoadImage(BitmapRequest request) {
        final String imageUrl = request.imageUri;
        InputStream is = null;
        try {
            URL url = new URL(imageUrl);
            final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            is = new BufferedInputStream(conn.getInputStream());
            is.mark(is.available());

            final InputStream inputStream = is;
            BitmapDecoder bitmapDecoder = new BitmapDecoder() {

                @Override
                public Bitmap decodeBitmapWithOption(BitmapFactory.Options options) {
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, options);
                    //
                    if (options.inJustDecodeBounds) {
                        try {
                            inputStream.reset();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        // 关闭流
                        conn.disconnect();
                    }
                    return bitmap;
                }
            };

            return bitmapDecoder.decodeBitmap(request.getImageViewWidth(),
                    request.getImageViewHeight());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtil.closeQuietly(is);
        }

        return null;
    }
}
