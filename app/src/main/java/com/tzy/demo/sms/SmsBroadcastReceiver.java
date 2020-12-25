package com.tzy.demo.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import java.util.Arrays;

/**
 * Created by tang
 * 2019-05-30
 */
public class SmsBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "SmsBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        SmsMessage[] smsMessages = null;
        Object[] pdus = null;
        String format = null;
        if (bundle != null) {
            pdus = (Object[]) bundle.get("pdus");
            format = (String) bundle.get("format");
        }
        if (pdus !=null){
            smsMessages = new SmsMessage[pdus.length];
            String sender = null;
            String content = null;
            
            for (int i=0; i<pdus.length; i++){
                Log.e(TAG, "puds->" + Arrays.toString((byte[]) pdus[i]));
                smsMessages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                sender = smsMessages[i].getOriginatingAddress(); // 获取短信的发送者
                content = smsMessages[i].getMessageBody(); // 获取短信的内容
                Log.e(TAG, "body->" + content);
                Log.e(TAG, "body->" + content);

            }
        }
    }
}
