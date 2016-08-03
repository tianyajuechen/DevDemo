package com.example.refreshdemo.okhttp;

import com.example.refreshdemo.BuildConfig;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by YANG
 * 2016/5/11 23:30
 */
public class OkHttpUtil {
    public static OkHttpClient client;
    private OkHttpClient.Builder builder;

    // Define the interceptor, add authentication headers
    Interceptor interceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request().newBuilder()
                    //.header("Accept-Encoding", "gzip")
                    //.addHeader("Accept-Encoding", "gzip")//加上这个后 balance接口获取不到数据
                    //.addHeader("User-Agent", "Mozilla/5.0 (Linux; Android 5.1.1; Nexus 6 Build/LYZ28E) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.23 Mobile Safari/537.36")
                    .build();
            return chain.proceed(request);
        }
    };

    public OkHttpUtil() {
        client = new OkHttpClient();
        builder = new OkHttpClient.Builder();
        builder.cookieJar(new CookiesManager())
                .retryOnConnectionFailure(true)
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS);
        builder.addNetworkInterceptor(interceptor);//网络拦截器不会在log里打印
        //builder.addInterceptor(interceptor);
        if (BuildConfig.DEBUG) {
            //打印请求log
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logging);
        }
        client = builder.build();
    }

    public static OkHttpClient getInstance() {
        if (client == null) {
            new OkHttpUtil();
        }
        return client;
    }
}
