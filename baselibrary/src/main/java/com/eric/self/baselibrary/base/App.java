package com.eric.self.baselibrary.base;

import android.app.Application;
import android.content.Context;

import com.eric.self.baselibrary.util.AppContextUtil;


/**
 * Created by laucherish on 16/3/17.
 */
public class App extends Application {

    private static Context mApplicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationContext = this;
        AppContextUtil.init(this);
    }

    // 获取ApplicationContext
    public static Context getContext() {
        return mApplicationContext;
    }
}
