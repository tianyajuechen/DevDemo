package com.tzy.demo.activity.memoryleak;

import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import com.tzy.demo.R;

public class MemoryLeakActivity extends AppCompatActivity {

    EditText et;
    TextView tv;

    private Handler handler = new Handler();
    int count;

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (count < 1) return;
            Log.e(MemoryLeakActivity.class.getSimpleName(), "count="+count);
            count--;
            handler.postDelayed(this, 1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_leak);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = Integer.parseInt(et.getText().toString());
                handler.postDelayed(runnable, 1000);
            }
        });
    }
}
