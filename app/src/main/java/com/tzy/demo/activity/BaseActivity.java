package com.tzy.demo.activity;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        log("onDestroy");
    }

    public abstract int getLayoutId();

    protected void log(String msg) {
        Log.e(TAG, msg);
    }
}
