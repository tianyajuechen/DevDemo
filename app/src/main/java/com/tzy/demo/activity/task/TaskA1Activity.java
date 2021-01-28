package com.tzy.demo.activity.task;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.tzy.demo.R;
import com.tzy.demo.activity.BaseActivity;

public class TaskA1Activity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_a1);
        init();
    }

    protected void init() {
        setText(TaskA2Activity.class);
    }

    protected void setText(Class<?> cls) {
        TextView tv = findViewById(R.id.tv);
        tv.setText(this.getClass().getSimpleName());
        tv.setOnClickListener(v -> {
            startActivity(new Intent(this, cls));
        });
    }
}