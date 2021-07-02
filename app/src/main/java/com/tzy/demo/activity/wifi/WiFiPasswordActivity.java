package com.tzy.demo.activity.wifi;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.tzy.demo.R;

import java.util.List;

public class WiFiPasswordActivity extends Activity {

    public static final String DATE_LIST = "DATE_LIST";
    public static final String SWITCH = "SWITCH";
    private static final String TAG = "WiFiPasswordActivity";

    ListView mLv;

    private WifiManage wifiManage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_password);

        mLv = findViewById(R.id.lv);

        wifiManage = new WifiManage();
        try {
            Init();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void Init() throws Exception {
        List<WifiInfo> wifiInfos = wifiManage.Read();
        WifiAdapter ad = new WifiAdapter(wifiInfos,WiFiPasswordActivity.this);
        mLv.setAdapter(ad);
    }

    public class WifiAdapter extends BaseAdapter{

        List<WifiInfo> wifiInfos =null;
        Context con;

        public WifiAdapter(List<WifiInfo> wifiInfos,Context con){
            this.wifiInfos =wifiInfos;
            this.con = con;
        }

        @Override
        public int getCount() {
            return wifiInfos.size();
        }

        @Override
        public Object getItem(int position) {
            return wifiInfos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(con).inflate(android.R.layout.simple_list_item_1, null);
            TextView tv = convertView.findViewById(android.R.id.text1);
            tv.setText("Wifi："+wifiInfos.get(position).Ssid+"\n密码："+wifiInfos.get(position).Password);
            return convertView;
        }

    }
}
