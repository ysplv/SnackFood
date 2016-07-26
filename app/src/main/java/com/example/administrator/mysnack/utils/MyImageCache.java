package com.example.administrator.mysnack.utils;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by Administrator on 2016/7/4 0004.
 */
public class MyImageCache implements ImageLoader.ImageCache {
    private LruCache<String, Bitmap> lruCache;

    public MyImageCache() {
        //运行时八分之一大小
        int max = (int) (Runtime.getRuntime().maxMemory() / 8);
        lruCache = new LruCache<String, Bitmap>(max) {
            //获取图片大小
            @Override
            protected int sizeOf(String key, Bitmap value) {

                return value.getByteCount();
            }
        };
    }

    /**
     * 获取图片
     *
     * @param url
     * @return
     */
    @Override
    public Bitmap getBitmap(String url) {

        return lruCache.get(url);
    }

    /**
     * 存图片
     *
     * @param url
     * @param bitmap
     */
    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        lruCache.put(url, bitmap);
    }
}
