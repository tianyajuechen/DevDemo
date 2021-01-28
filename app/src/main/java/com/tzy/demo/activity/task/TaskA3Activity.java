package com.tzy.demo.activity.task;

import android.content.Intent;
import android.widget.TextView;
import com.tzy.demo.R;

/**
 * @Author tangzhaoyang
 * @Date 2021/1/28
 */
public class TaskA3Activity extends TaskA1Activity {
    @Override
    protected void init() {
        TextView tv = findViewById(R.id.tv);
        tv.setText(this.getClass().getSimpleName());
        tv.setOnClickListener(v -> {
            startActivity(new Intent(this, TaskB1Activity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        });
    }
}
