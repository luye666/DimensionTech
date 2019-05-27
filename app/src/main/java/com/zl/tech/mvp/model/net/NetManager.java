package com.zl.tech.mvp.model.net;


import android.util.Log;

import com.zl.tech.application.MyApplication;
import com.zl.tech.mvp.model.api.Api;
import com.zl.tech.mvp.model.api.ApiService;
import com.zl.tech.mvp.model.utils.Base64;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

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

/**
 * @author Administrator QQ:1228717266
 * @name DimensionTech
 * @time 2018/11/29 19:01
 */
public class NetManager {

    private static final int DEFAULT_TIME_OUT = 5;//超时时间 5s
    private static final int DEFAULT_READ_TIME_OUT = 10;
    public ApiService apiService;
    private Retrofit mRetrofit;
    public static final String CACHE_NAME = "xxx";
    private String CER_SSL = "-----BEGIN CERTIFICATE-----\n" +
            "MIIDXTCCAkUCCQDNhr7+xMtU3jANBgkqhkiG9w0BAQUFADBoMQswCQYDVQQGEwJD\n" +
            "TjELMAkGA1UECAwCeDExCzAJBgNVBAcMAngyMQswCQYDVQQKDAJ4MzELMAkGA1UE\n" +
            "CwwCeDUxCzAJBgNVBAMMAmJ3MRgwFgYJKoZIhvcNAQkBFgkxQDE2My5jb20wHhcN\n" +
            "MTgwOTE3MTEyNjI2WhcNMjgwOTE0MTEyNjI2WjB5MQswCQYDVQQGEwJDTjELMAkG\n" +
            "A1UECAwCeDExCzAJBgNVBAcMAngyMQ4wDAYDVQQKDAViYXdlaTEPMA0GA1UECwwG\n" +
            "YmF3ZWkyMQswCQYDVQQDDAJidzEiMCAGCSqGSIb3DQEJARYTMTg2MDAxNTE1NjhA\n" +
            "MTYzLmNvbTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAMRn5BnuG5Qm\n" +
            "GBJV+aBdVpCPkXNs7sCZPpKT6K6gi1t2L88DOiZqsIi+06KsN55w/51YeuAHYq9w\n" +
            "QFx9X34eFB/n//SKA/qkWznxZdtsAJUD3hkKkR3jhj+JP1EZWxwgIP5Dp1RyuBxH\n" +
            "gEoe7UmK9o/V2hJ3HTAYF20vQquFltucl5svnmtQvF4aofhFQ3gqXYvXD6pxcIuI\n" +
            "UOePK49hnlxz7v5t5s/0VXHHz+5THsEg14oW+kAPFKVPS59tjQV7LzDMXjunEBzc\n" +
            "A/Jslafx32BF4Fy1aCbWCmIJSKou9MBnrP1MuheIpMO1qEMBXx/9MuLMFdnyj20N\n" +
            "9M+WlaMBiMUCAwEAATANBgkqhkiG9w0BAQUFAAOCAQEAJf/W2zTuf9D36js7766T\n" +
            "xpfWCVy0POqkdXNKvPThd/U6Qwi2QXc0CmNvr02lfVRu11cX4inR9RiJUXWoeG7J\n" +
            "DDWBSBPKTpeF8+k2w+DjDAkE3mj3iCQdeydkhCUYquSxtFNC6mFZ9zrkMs7sGuBc\n" +
            "GoDnueL8B2IiNfLtA3vUzvAkqh9b7rOBk1VXem4JFnIoisFufdzH1RhNWxZTgtlG\n" +
            "+Po5VSrMpKgtPYLHFIprMIUwGfW7j36hhvnEArEVXLWjY3hhNvyJ4jBf0WRp44GA\n" +
            "8OZ1zDEyVxxtOAQXXlfiYusPuy5Wup2P7RYo17xMVoHeQg6yF+iszlBHoJ5250iv\n" +
            "kA==\n" +
            "-----END CERTIFICATE-----";

    public static NetManager getInstance() {
        return NetInstance.ourInstance;
    }

    private NetManager() {

        // 创建 OKHttpClient
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);//连接超时时间
        builder.writeTimeout(DEFAULT_READ_TIME_OUT,TimeUnit.SECONDS);//写操作 超时时间
        builder.readTimeout(DEFAULT_READ_TIME_OUT,TimeUnit.SECONDS);//读操作超时时间

        byte[] decode = Base64.decode("当前版本=1.0.0 平台=安卓 产品=影院项目 渠道=官方渠道");
        // 添加公共参数拦截器
        HttpCommonInterceptor commonInterceptor = new HttpCommonInterceptor.Builder()
                .addHeaderParams("ak",String.valueOf(decode))
                .addHeaderParams("Content-Type","application/x-www-form-urlencoded")
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


        try {
            SSLContext instance = SSLContext.getInstance("TLS");
            TrustManager myTrustManager = new MyTrustManager(readCert(CER_SSL));
            instance.init(null,new TrustManager[]{myTrustManager},null);
            builder.sslSocketFactory(instance.getSocketFactory(), (X509TrustManager) myTrustManager)
            .hostnameVerifier(hostnameVerifier);
        } catch (Exception e) {
            e.printStackTrace();
        }


        // 创建Retrofit
        mRetrofit = new Retrofit.Builder()
                .client(builder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Api.BASE_URL)
                .build();

        apiService = mRetrofit.create(ApiService.class);
    }
    //主机地址验证
    final HostnameVerifier hostnameVerifier = new HostnameVerifier() {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return hostname.equals("172.17.8.100");
        }
    };

    /**
     * 根据字符串读取出证书
     * @param cer
     * @return
     */
    private static X509Certificate readCert(String cer) {
        if (cer == null || cer.trim().isEmpty())
            return null;
        InputStream caInput = new ByteArrayInputStream(cer.getBytes());
        X509Certificate cert = null;
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            cert = (X509Certificate) cf.generateCertificate(caInput);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (caInput != null) {
                    caInput.close();
                }
            } catch (Throwable ex) {
            }
        }
        return cert;
    }




   private static class NetInstance{
        private static final NetManager ourInstance = new NetManager();
    }

    /**
     * 实现了 X509TrustManager
     * 通过此类中的 checkServerTrusted 方法来确认服务器证书是否正确
     */
    class MyTrustManager implements X509TrustManager {
        X509Certificate cert;

        MyTrustManager(X509Certificate cert) {
            this.cert = cert;
        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            // 我们在客户端只做服务器端证书校验。
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            // 确认服务器端证书和代码中 hard code 的 CRT 证书相同。
            if (chain[0].equals(this.cert)) {
                Log.i("Jin", "checkServerTrusted Certificate from server is valid!");
                return;// found match
            }
            throw new CertificateException("checkServerTrusted No trusted server cert found!");
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }


}
