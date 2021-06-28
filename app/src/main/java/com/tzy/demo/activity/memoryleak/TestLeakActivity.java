package com.tzy.demo.activity.memoryleak;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import com.tzy.demo.R;

public class TestLeakActivity extends AppCompatActivity {

    TextView tv;

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_leak);
        tv = (TextView) findViewById(R.id.tv);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TestLeakActivity.this, MemoryLeakActivity.class));
            }
        });
        Log.e("aa", "onCreate is in " + Thread.currentThread().getId());


        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Log.e("aa", "runnable is in " + Thread.currentThread().getId());
            }
        };

        handler.post(runnable);
    }
}
