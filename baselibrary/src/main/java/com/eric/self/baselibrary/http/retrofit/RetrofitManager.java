package com.eric.self.baselibrary.http.retrofit;

import android.content.Context;
import android.support.v4.util.SimpleArrayMap;

import com.baronzhang.retrofit2.converter.FastJsonConverterFactory;
import com.eric.self.baselibrary.base.App;
import com.eric.self.baselibrary.util.AppContextUtil;
import com.eric.self.baselibrary.util.NetUtils;
import com.eric.self.baselibrary.util.SPUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by laucherish on 16/3/15.
 */
public class RetrofitManager {


    //短缓存有效期为1分钟
    public static final int CACHE_STALE_SHORT = 60;
    //长缓存有效期为7天
    public static final int CACHE_STALE_LONG = 60 * 60 * 24 * 7;

    public static final String CACHE_CONTROL_AGE = "Cache-Control: public, max-age=";

    //查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
    public static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_LONG;
    //查询网络的Cache-Control设置，头部Cache-Control设为max-age=0时则不会使用缓存而请求服务器
    public static final String CACHE_CONTROL_NETWORK = "max-age=0";
    private static OkHttpClient mOkHttpClient;
    private final Retrofit mRetrofit;
    private static RetrofitManager mRetrofitManager;
    public final static String BASE_URL = "base_url";

    private SimpleArrayMap<Class, Object> mServices = new SimpleArrayMap<>();


    public static RetrofitManager newInstance(Context context) {
        if (mRetrofitManager == null) {
            mRetrofitManager = new RetrofitManager(context);
        }
        return mRetrofitManager;
    }

    public <T> T getService(Class<T> clasz) {
        final T t = (T) mServices.get(clasz);
        if (t != null) {
            return t;
        }
        synchronized (mServices) {
            if (mServices.indexOfKey(clasz) < 0) {
                final T instance = mRetrofitManager.mRetrofit.create(clasz);
                mServices.put(clasz, instance);
                return instance;
            } else {
                return (T) mServices.get(clasz);
            }
        }
    }

    private RetrofitManager(Context context) {
        initOkHttpClient();
        mRetrofit = new Retrofit.Builder()
                .baseUrl((String) SPUtils.get(context, BASE_URL, ""))
                .client(mOkHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(FastJsonConverterFactory.create())
                .build();
    }

    private void initOkHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        if (mOkHttpClient == null) {
            synchronized (RetrofitManager.class) {
                if (mOkHttpClient == null) {

                    // 指定缓存路径,缓存大小100Mb
                    Cache cache = new Cache(new File(App.getContext().getCacheDir(), "HttpCache"),
                            1024 * 1024 * 100);

                    mOkHttpClient = new OkHttpClient.Builder()
                            .cache(cache)
                            .addInterceptor(mRewriteCacheControlInterceptor)
                            .addNetworkInterceptor(mRewriteCacheControlInterceptor)
                            .addInterceptor(interceptor)
                            .retryOnConnectionFailure(true)
                            .connectTimeout(15, TimeUnit.SECONDS)
                            .build();
                }
            }
        }
    }

    // 云端响应头拦截器，用来配置缓存策略
    private Interceptor mRewriteCacheControlInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetUtils.isConnected(AppContextUtil.getInstance())) {
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
            }
            Response originalResponse = chain.proceed(request);
            if (NetUtils.isConnected(AppContextUtil.getInstance())) {
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder().header("Cache-Control", cacheControl)
                        .removeHeader("Pragma").build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_LONG)
                        .removeHeader("Pragma").build();
            }
        }
    };
}
