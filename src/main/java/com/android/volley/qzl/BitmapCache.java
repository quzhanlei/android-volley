package com.android.volley.qzl;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.CancellationSignal;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by QZL on 16/7/17.
 * 图片的内存缓存
 */

public class BitmapCache implements ImageLoader.ImageCache {

    //是个map
    private LruCache<String,Bitmap> mLruCache;

    public BitmapCache() {
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int maxCacheSize = maxMemory/8;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            mLruCache = new LruCache<String, Bitmap>(maxCacheSize){
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
                        return value.getByteCount();
                    }
                    return 0;
                }
            };
        }


    }


    @Override
    public Bitmap getBitmap(String url) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            return mLruCache.get(url);
        }
        return null;
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB_MR1) {
            mLruCache.put(url, bitmap);
        }
    }
}
