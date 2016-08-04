package com.example.refreshdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.refreshdemo.activity.RefreshActivity1;
import com.example.refreshdemo.activity.RefreshActivity2;
import com.example.refreshdemo.activity.RefreshActivity3;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.bt_1)
    Button mBt1;
    @Bind(R.id.bt_2)
    Button mBt2;
    @Bind(R.id.bt_3)
    Button mBt3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mBt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RefreshActivity1.class));
            }
        });

        mBt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RefreshActivity2.class));
            }
        });

        mBt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RefreshActivity3.class));
            }
        });
    }
}
