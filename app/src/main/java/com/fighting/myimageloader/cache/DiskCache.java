package com.fighting.myimageloader.cache;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.fighting.myimageloader.request.BitmapRequest;
import com.fighting.myimageloader.utils.BitmapDecoder;
import com.fighting.myimageloader.utils.MD5Utils;
import com.jakewharton.disklrucache.DiskLruCache;
import com.jakewharton.disklrucache.IOUtil;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 描述：
 * Created by MaJD on 2016/8/4.
 */

public class DiskCache implements BitmapCache {
    private static final int MB = 1024 * 1024;
    private static final String IMAGE_DISK_CACHE_DIR = "bitmap";

    private DiskLruCache mDiskLruCache;

    private static DiskCache mDiskCache;

    private DiskCache(Context context) {
        initDiskCache(context);
    }

    private void initDiskCache(Context context) {
        try {
            File cacheDir = getDiskCacheDir(context, IMAGE_DISK_CACHE_DIR);
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            mDiskLruCache = DiskLruCache.open(cacheDir, getAppVersion(context), 1, 50 * MB);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * 获取sd缓存的目录,如果挂载了sd卡则使用sd卡缓存，否则使用应用的缓存目录。
     *
     * @param uniqueName 缓存目录名,比如bitmap
     */
    private File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    public static DiskCache getInstance(Context context) {
        if (mDiskCache == null) {
            synchronized (DiskCache.class) {
                if (mDiskCache == null) {
                    mDiskCache = new DiskCache(context);
                }
            }
        }
        return mDiskCache;
    }

    @Override
    public synchronized Bitmap get(final BitmapRequest bean) {
        // 图片解析器
        BitmapDecoder decoder = new BitmapDecoder() {

            @Override
            public Bitmap decodeBitmapWithOption(BitmapFactory.Options options) {
                final InputStream inputStream = getInputStream(bean.imageUriMd5);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null,
                        options);
                IOUtil.closeQuietly(inputStream);
                return bitmap;
            }
        };

        return decoder.decodeBitmap(bean.getImageViewWidth(),
                bean.getImageViewHeight());
    }

    private InputStream getInputStream(String md5) {
        DiskLruCache.Snapshot snapshot;
        try {
            snapshot = mDiskLruCache.get(md5);
            if (snapshot != null) {
                return snapshot.getInputStream(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * sd卡缓存只缓存从网络下下载下来的图片,本地图片则不缓存
     */
    @Override
    public void put(BitmapRequest request, Bitmap value) {
        if (request.justCacheInMem) {
            Log.e(IMAGE_DISK_CACHE_DIR, "### 仅缓存在内存中");
            return;
        }

        DiskLruCache.Editor editor = null;
        try {
            // 如果没有找到对应的缓存，则准备从网络上请求数据，并写入缓存
            editor = mDiskLruCache.edit(request.imageUriMd5);
            if (editor != null) {
                OutputStream outputStream = editor.newOutputStream(0);
                if (writeBitmapToDisk(value, outputStream)) {
                    // 写入disk缓存
                    editor.commit();
                } else {
                    editor.abort();
                }
                IOUtil.closeQuietly(outputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean writeBitmapToDisk(Bitmap bitmap, OutputStream outputStream) {
        BufferedOutputStream bos = new BufferedOutputStream(outputStream, 8 * 1024);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        boolean result = true;
        try {
            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();
            result = false;
        } finally {
            IOUtil.closeQuietly(bos);
        }

        return result;
    }

    @Override
    public void remove(BitmapRequest key) {
        try {
            mDiskLruCache.remove(MD5Utils.toMD5(key.imageUriMd5));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
