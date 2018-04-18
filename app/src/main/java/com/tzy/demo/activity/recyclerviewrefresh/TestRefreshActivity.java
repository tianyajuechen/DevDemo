package com.tzy.demo.activity.recyclerviewrefresh;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.tzy.demo.R;

public class TestRefreshActivity extends AppCompatActivity {
    @BindView(R.id.bt_1)
    Button mBt1;
    @BindView(R.id.bt_2)
    Button mBt2;
    @BindView(R.id.bt_3)
    Button mBt3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_refresh);
        ButterKnife.bind(this);

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
