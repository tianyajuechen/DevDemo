package com.tzy.demo.sms;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import com.tzy.demo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReadSmsActivity extends Activity {

    private ListView mListView;
    private Button mBt;
    private SimpleAdapter sa;
    private List<Map<String, Object>> data;
    public static final int REQ_CODE_CONTACT = 1;

    final String SMS_URI_ALL = "content://sms/"; // 所有短信
    final String SMS_URI_INBOX = "content://sms/inbox"; // 收件箱
    final String SMS_URI_SEND = "content://sms/sent"; // 已发送
    final String SMS_URI_DRAFT = "content://sms/draft"; // 草稿
    final String SMS_URI_OUTBOX = "content://sms/outbox"; // 发件箱
    final String SMS_URI_FAILED = "content://sms/failed"; // 发送失败
    final String SMS_URI_QUEUED = "content://sms/queued"; // 待发送列表

    private  boolean mAudio;
    private  boolean mMore;


    private Handler mHandler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Log.e("aa", "0");
                    mAudio = true;

                    break;
                case 1:
                    Log.e("aa", "1");
                    mMore = true;

                    break;
                default:
                    break;
            }
            return true;
        }
    });


    public void aaa() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                Looper.prepare();
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message message = new Message();
                message.what = 0;
                mHandler.sendMessage(message);
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {

                Looper.prepare();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(1);
            }
        }).start();


        do {

            String s = "";
            if (mAudio && mMore) {
                String ss = "";
                break;
            }
        } while (true);

        String bb = "'";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_sms);

        initView();

        aaa();
    }

    private void initView() {
        //得到ListView
        mListView = (ListView) findViewById(R.id.listView);
        mBt = findViewById(R.id.bt);
        data = new ArrayList<Map<String, Object>>();
        //配置适配置器S
        sa = new SimpleAdapter(this, data, android.R.layout.simple_list_item_2,
                new String[]{"names", "message"}, new int[]{android.R.id.text1,
                android.R.id.text2});
        mListView.setAdapter(sa);

        mBt.setOnClickListener(v -> readSMS());
    }

    /**
     * 检查申请短信权限
     */
    private void checkSMSPermission() {
        String[] permissions = new String[]{Manifest.permission.READ_SMS
                , Manifest.permission.RECEIVE_SMS
                , Manifest.permission.SEND_SMS};
        int count = 0;
        //逐个判断是否还有未通过的权限
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                count++;
            }
            if (count > 0) {
                //向系统申请权限
                ActivityCompat.requestPermissions(this, permissions, REQ_CODE_CONTACT);
            } else {
                query();
            }
        }
    }

    /**
     * 点击读取短信
     */
    public void readSMS() {
        checkSMSPermission();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        //判断用户是否，同意 获取短信授权
        if (requestCode == REQ_CODE_CONTACT && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //获取到读取短信权限
            query();
        } else {
            Toast.makeText(this, "未获取到短信权限", Toast.LENGTH_SHORT).show();
        }
    }

    private void query() {

//        //读取所有短信
        Uri uri = Uri.parse(SMS_URI_INBOX);
        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(uri, new String[]{"_id", "address", "body", "date", "type"}, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            int _id;
            String address;
            String body;
            String date;
            int type;
            while (cursor.moveToNext()) {
                Map<String, Object> map = new HashMap<String, Object>();
                _id = cursor.getInt(0);
                address = cursor.getString(1);
                body = cursor.getString(2);
                date = cursor.getString(3);
                type = cursor.getInt(4);
                map.put("names", address);
                map.put("message", _id);

                Log.i("test", "_id=" + _id + " address=" + address + " body=" + body + " date=" + date + " type=" + type);
                data.add(map);
                //通知适配器发生改变
                sa.notifyDataSetChanged();
            }
        }
    }
}
