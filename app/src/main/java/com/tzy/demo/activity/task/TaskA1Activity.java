package com.tzy.demo.activity.task;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.tzy.demo.R;
import com.tzy.demo.activity.BaseActivity;
import com.tzy.demo.databinding.ActivityTaskA1Binding;

public class TaskA1Activity extends BaseActivity<ActivityTaskA1Binding> {

    private final String TAG = "TaskA1Activity";
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();

        handler = new Handler();
        handler.postDelayed(() -> Log.e(TAG, "延迟3秒"), 3 * 1000);
        handler.postDelayed(() -> Log.e(TAG, "延迟4秒"), 4 * 1000);
        handler.postDelayed(() -> Log.e(TAG, "延迟5秒"), 5 * 1000);
        handler.postDelayed(() -> Log.e(TAG, "延迟6秒"), 6 * 1000);
        handler.postDelayed(() -> Log.e(TAG, "延迟7秒"), 7 * 1000);
        handler.postDelayed(() -> Log.e(TAG, "延迟8秒"), 8 * 1000);
        handler.postDelayed(() -> Log.e(TAG, "延迟9秒"), 9 * 1000);
    }

    protected void init() {
        setText(TaskA2Activity.class);
    }

    protected void setText(Class<?> cls) {
        mBinding.tv.setText(this.getClass().getSimpleName());
        mBinding.tv.setOnClickListener(v -> {
            startActivity(new Intent(this, cls));
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeMessages(0);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_task_a1;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {

    }
}