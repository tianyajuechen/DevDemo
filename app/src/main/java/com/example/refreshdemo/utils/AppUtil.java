package com.example.refreshdemo.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.refreshdemo.application.MyApp;

/**
 * Created by YANG
 * 2016/5/6 11:15
 * 封装了一些工具类
 */
public class AppUtil {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static boolean isNetAvailable() {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) MyApp.getInstance().mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo[] info = connectivity.getAllNetworkInfo();
                if (info != null) {
                    for (int i = 0; i < info.length; i++) {
                        if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                            return true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**关闭系统输入法*/
    public static final void hideKeyBoard(Context context,View view){
        if(view==null || context ==null) return;
        InputMethodManager inputManager = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    /**弹出系统输入法*/
    public static void showSystemInputBord(EditText et){
        et.requestFocusFromTouch();
        InputMethodManager inputManager = (InputMethodManager) et.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(et, 0);
    }

    /** 获取屏幕的宽度 */
    public static int getScreenWidth() {
        return MyApp.getInstance().getResources().getDisplayMetrics().widthPixels;
    }

    /***
     * 获取屏幕的高度
     *
     * @return
     */
    public static int getScreenHeight() {
        return MyApp.getInstance().getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 获得当前版本的versionCode
     */
    public static String getVersionName(){
        try {
            return MyApp.getInstance().getPackageManager().getPackageInfo(MyApp.getInstance().getPackageName(), 0).versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Unkown";
    }

    /**
     * 对比两个版本大小
     * @param version1
     * @param version2
     * @return -1：小于 0：相等 1：大于
     */
    public static int compareVersion(String version1, String version2) {
        //当前版本号拆成数组
        String[] arr1 = version1.split("\\.");
        //最新版本号拆成数组
        String[] arr2 = version2.split("\\.");
        //for循环最大次数
        int maxLength = arr1.length > arr2.length ? arr1.length : arr2.length;
        //返回结果 -1：小于 0：相等 1：大于
        int result = 0;

        for (int i = 0; i < maxLength; i++) {
            String str1;
            String str2;
            try {
                str1 = arr1[i];
            } catch (Exception e) {
                e.printStackTrace();
                str1 = "0";
            }
            try {
                str2 = arr2[i];
            } catch (Exception e) {
                e.printStackTrace();
                str2 = "0";
            }
            if (Integer.parseInt(str1) > Integer.parseInt(str2)) {
                return 1;
            } else if (Integer.parseInt(str1) < Integer.parseInt(str2)) {
                return -1;
            } else {
                if (i == maxLength - 1) {
                    return 0;
                }
            }
        }
        return result;
    }
}
