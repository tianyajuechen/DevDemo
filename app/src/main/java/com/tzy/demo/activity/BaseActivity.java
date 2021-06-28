package com.tzy.demo.activity;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

/**
 * @Author tangzhaoyang
 * @Date 2021/1/28
 */
public abstract class BaseActivity<B extends ViewDataBinding> extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();

    protected B mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, getLayoutId());
        log("onCreate");

        initView();
        initEvent();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        log("onDestroy");
    }

    public abstract int getLayoutId();

    public abstract void initView();

    public abstract void initEvent();

    protected void log(String msg) {
        Log.e(TAG, msg);
    }
}
