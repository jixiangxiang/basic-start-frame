package com.eric.self.selfapplication.base;

import com.eric.self.baselibrary.base.App;
import com.eric.self.baselibrary.http.retrofit.RetrofitManager;
import com.eric.self.baselibrary.util.SPUtils;
import com.growingio.android.sdk.collection.Configuration;
import com.growingio.android.sdk.collection.GrowingIO;
import com.zhuge.analysis.stat.ZhugeSDK;

/**
 * Created by eric on 2017/8/11.
 */

public class MyApplication extends App {

    @Override
    public void onCreate() {
        super.onCreate();
        GrowingIO.startWithConfiguration(this, new Configuration()
                .useID()
                .trackAllFragments()
                .setChannel("SelfTest"));
        ZhugeSDK.getInstance().openDebug();
        //初始化分析跟踪
        ZhugeSDK.getInstance().init(getApplicationContext());
        SPUtils.put(this, RetrofitManager.BASE_URL, "https://api.github.com/");
    }
}
