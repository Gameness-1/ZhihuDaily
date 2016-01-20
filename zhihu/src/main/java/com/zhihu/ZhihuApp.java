package com.zhihu;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by bjweilingzi on 2016/1/19.
 */
public class ZhihuApp extends Application {
    public static ZhihuApp mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        Fresco.initialize(this);
    }

    public static ZhihuApp getZhihuAppInstance() {
        if (mApplication == null)
            throw new NullPointerException("mApplication not create or be terminited");
        return mApplication;
    }
}
