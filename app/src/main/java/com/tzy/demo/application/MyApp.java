package com.tzy.demo.application;

import android.app.Application;
import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tzy.demo.api.APIService;
import com.tzy.demo.finals.Urls;
import com.tzy.demo.okhttp.OkHttpUtil;
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

    /**
     * 请求队列
     */

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        mContext = this.getApplicationContext();

        initRetrofit();

//        WebView.setWebContentsDebuggingEnabled(true);

        initBugly();
    }

    //腾讯Bugly初始化
    private void initBugly() {
        /*CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(getApplicationContext());
        strategy.setAppChannel("官方");
        CrashReport.initCrashReport(getApplicationContext(), "da37a68e8a", BuildConfig.DEBUG, strategy);
        CrashReport.putUserData(this, "UMengId", "123456");*/
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
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Urls.BaseUrl)
                .client(OkHttpUtil.getInstance())
                .build();
        mApiService = mRetrofit.create(APIService.class);
    }

}
