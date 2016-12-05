package com.tzy.demo.application;

import android.app.Application;
import android.content.Context;
import com.tzy.demo.api.APIService;
import com.tzy.demo.finals.Urls;
import com.tzy.demo.okhttp.OkHttpUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by YANG
 * 2016/5/5 13:15
 */
public class MyApp extends Application {
    private static MyApp mApp;
    public Context mContext;
    public Retrofit mRetrofit;
    public APIService mApiService;

    /**请求队列*/

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        mContext = this.getApplicationContext();

        initRetrofit();
    }

    public static MyApp getInstance() {
        if (mApp == null) {
            mApp = new MyApp();
        }
        return mApp;
    }

    /**
     * 初始化Retrofit
     * 转移到入口Activity处调用
     */
    public void initRetrofit() {
        //设置Gson日期格式
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
        mRetrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(Urls.BaseUrl)
                .client(OkHttpUtil.getInstance())
                .build();
        mApiService = mRetrofit.create(APIService.class);
    }

}
