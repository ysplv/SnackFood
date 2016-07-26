package com.example.administrator.mysnack.app;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Administrator on 2016/7/26 0026.
 */
public class MyApp extends Application {
    private static RequestQueue queue;
    @Override
    public void onCreate() {
        super.onCreate();
        queue= Volley.newRequestQueue(this);
    }
    public static RequestQueue getQueue(){
        return queue;
    }
}
