package com.tzy.demo.activity.memoryleak;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.tzy.demo.R;

public class TestLeakActivity extends AppCompatActivity {

    @BindView(R.id.tv)
    TextView tv;

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_leak);
        ButterKnife.bind(this);

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
