package com.tzy.demo.activity.wifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.tzy.demo.utils.SPUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by tang
 * 2018/6/19
 */
public class WifiStateReceiver extends BroadcastReceiver {

    private static final String TAG = "WifiStateReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Bundle extras = intent.getExtras();
        Log.e(TAG, "actioin:" + action);
        Log.e(TAG, "extras->" + printBundle(extras));

        if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(action)) {//这个监听wifi的打开与关闭，与wifi的连接无关
            /*int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, -1);
            Log.e("WIFI状态", "wifiState:" + wifiState);
            switch (wifiState) {
                case WifiManager.WIFI_STATE_DISABLED:
                    Log.e("WIFI状态", "wifiState:WIFI_STATE_DISABLED");
                    break;
                case WifiManager.WIFI_STATE_DISABLING:
                    Log.e("WIFI状态", "wifiState:WIFI_STATE_DISABLING");
                    break;
                case WifiManager.WIFI_STATE_ENABLED:
                    Log.e("WIFI状态", "wifiState:WIFI_STATE_ENABLED");
                    break;
                case WifiManager.WIFI_STATE_ENABLING:
                    Log.e("WIFI状态", "wifiState:WIFI_STATE_ENABLING");
                    break;
                case WifiManager.WIFI_STATE_UNKNOWN:
                    Log.e("WIFI状态", "wifiState:WIFI_STATE_UNKNOWN");
                    break;
                //
            }*/
        }
        // 这个监听wifi的连接状态即是否连上了一个有效无线路由，当上边广播的状态是WifiManager.WIFI_STATE_DISABLING，和WIFI_STATE_DISABLED的时候，根本不会接到这个广播。
        // 在上边广播接到广播是WifiManager.WIFI_STATE_ENABLED状态的同时也会接到这个广播，当然刚打开wifi肯定还没有连接到有效的无线
        if (WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(action)) {
            NetworkInfo networkInfo = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            WifiInfo wifiInfo = intent.getParcelableExtra(WifiManager.EXTRA_WIFI_INFO);
            if (null != networkInfo) {
                NetworkInfo.State state = networkInfo.getState();
                if (state == NetworkInfo.State.CONNECTED) {
                    Log.e(TAG, "SSID -> " + wifiInfo.getSSID());
                    Boolean b = (Boolean) SPUtils.getData(WiFiPasswordActivity.SWITCH, false, context);
                    if (!b) return;
                    if (wifiInfo.getSSID().equals("\"wusi_33\"")) {
                        setRingerMode(AudioManager.RINGER_MODE_VIBRATE, context);
                        saveDate(context);
                    }
                }
            }
        }
    }

    private String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(WifiManager.EXTRA_WIFI_STATE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.get(key));
            }
        }
//        L.e("bundle:"+bundle);
        return sb.toString();
    }

    /**
     * 设置声音模式
     *
     * @param mode 声音模式
     */
    public void setRingerMode(int mode, Context context) {
        if (context == null) return;
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        if (audioManager == null) return;
        if (audioManager.getRingerMode() == mode) {
            return;
        }
        audioManager.setRingerMode(mode);
    }

    private void saveDate(Context context) {
        if (context == null) return;
        String date = (String) SPUtils.getData(WiFiPasswordActivity.DATE_LIST, "", context);
        String newDate = getCurrDate();
        if (!TextUtils.isEmpty(date)) {
            if (date.contains(newDate)) {
                newDate = date;
            } else {
                newDate = newDate.concat(",").concat(date);
            }
        }
        SPUtils.saveData(WiFiPasswordActivity.DATE_LIST, newDate, context);
    }

    /**
     * 获取当前日期
     *
     * @return
     */
    private String getCurrDate() {
        long a = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒", Locale.CHINA);
        return format.format(new Date(a));
    }
}
