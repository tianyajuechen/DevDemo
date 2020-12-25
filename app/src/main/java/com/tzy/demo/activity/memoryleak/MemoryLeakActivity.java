package com.tzy.demo.activity.memoryleak;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.tzy.demo.R;

public class MemoryLeakActivity extends AppCompatActivity {

    @BindView(R.id.et)
    EditText et;
    @BindView(R.id.tv)
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
        ButterKnife.bind(this);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = Integer.parseInt(et.getText().toString());
                handler.postDelayed(runnable, 1000);
            }
        });
    }
}
