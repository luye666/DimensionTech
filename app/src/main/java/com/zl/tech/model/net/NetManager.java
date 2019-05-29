package com.zl.tech.model.net;


import com.zl.tech.application.MyApplication;
import com.zl.tech.model.api.Api;
import com.zl.tech.model.api.ApiService;
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
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class NetManager {

    private static final int DEFAULT_TIME_OUT = 5;//超时时间 5s
    private static final int DEFAULT_READ_TIME_OUT = 10;
    public ApiService apiService;
    private Retrofit mRetrofit;
    public static final String CACHE_NAME = "xxx";

    public static NetManager getInstance() {
        return NetInstance.ourInstance;
    }

    private NetManager() {

        // 创建 OKHttpClient
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);//连接超时时间
        builder.writeTimeout(DEFAULT_READ_TIME_OUT,TimeUnit.SECONDS);//写操作 超时时间
        builder.readTimeout(DEFAULT_READ_TIME_OUT,TimeUnit.SECONDS);//读操作超时时间

        // 添加公共参数拦截器
        HttpCommonInterceptor commonInterceptor = new HttpCommonInterceptor.Builder()
//                .addHeaderParams("ak",String.valueOf(decode))
//                .addHeaderParams("Content-Type","application/x-www-form-urlencoded")
//                .addHeaderParams("userId",Contant.USER_ID)
//                .addHeaderParams("sessionId",Contant.SESSION_ID)
                .build();
        builder.addInterceptor(commonInterceptor);
        /**
         * 设置缓存
         */
        File cacheFile = new File(MyApplication.getContext().getExternalCacheDir(), CACHE_NAME);
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!NetworkUtils.isNetworkAvailable(MyApplication.getContext())) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);
                if (NetworkUtils.isNetworkAvailable(MyApplication.getContext())) {
                    int maxAge = 0;
                    // 有网络时 设置缓存超时时间0个小时
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader(CACHE_NAME)// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                            .build();
                } else {
                    // 无网络时，设置超时为4周
                    int maxStale = 60 * 60 * 24 * 28;
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .removeHeader(CACHE_NAME)
                            .build();
                }
                return response;
            }
        };
        builder.cache(cache).addInterceptor(cacheInterceptor);

        builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));


        // 创建Retrofit
        mRetrofit = new Retrofit.Builder()
                .client(builder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Api.BASE_URL)
                .build();

        apiService = mRetrofit.create(ApiService.class);
    }

   private static class NetInstance{
        private static final NetManager ourInstance = new NetManager();
    }


}
