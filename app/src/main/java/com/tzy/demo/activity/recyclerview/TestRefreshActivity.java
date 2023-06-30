package com.tzy.demo.activity.recyclerview;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


import com.tzy.demo.R;
import com.tzy.demo.activity.base.BaseActivity;
import com.tzy.demo.activity.paging.PagingTestActivity;
import com.tzy.demo.databinding.ActivityTestRefreshBinding;

public class TestRefreshActivity extends BaseActivity<ActivityTestRefreshBinding> {


    @Override
    public int getLayoutId() {
        return R.layout.activity_test_refresh;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {
        mBinding.bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TestRefreshActivity.this, RefreshActivity1.class));
            }
        });

        mBinding.bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TestRefreshActivity.this, RefreshActivity2.class));
            }
        });

        mBinding.bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TestRefreshActivity.this, RefreshActivity3.class));
            }
        });

        mBinding.bt4.setOnClickListener( v -> startActivity(new Intent(this, PagingTestActivity.class)));
    }

}
