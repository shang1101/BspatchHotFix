package com.shang.bspatchhotfix;

import android.app.Application;
import android.content.Context;

import com.shang.bspatchhotfix.utils.DexInject;

/**
 * Created by wangyan-pd on 2016/7/18.
 */
public class PHFApplication extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        DexInject.init(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
