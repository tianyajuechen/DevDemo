package com.tzy.demo.activity.recyclerview;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


import com.tzy.demo.R;

public class TestRefreshActivity extends AppCompatActivity {

    Button mBt1;
    Button mBt2;
    Button mBt3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_refresh);
        mBt1 = (Button) findViewById(R.id.bt_1);
        mBt2 = (Button) findViewById(R.id.bt_2);
        mBt3 = (Button) findViewById(R.id.bt_3);


        mBt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TestRefreshActivity.this, RefreshActivity1.class));
            }
        });

        mBt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TestRefreshActivity.this, RefreshActivity2.class));
            }
        });

        mBt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TestRefreshActivity.this, RefreshActivity3.class));
            }
        });
    }
}
