package com.tzy.demo.activity.material;

import com.tzy.demo.R;
import com.tzy.demo.activity.base.BaseActivity;
import com.tzy.demo.databinding.ActivityCoordinatorBinding;

/**
 * @Author tangzhaoyang
 * @Date 2021/7/22
 */
public class CoordinatorActivity extends BaseActivity<ActivityCoordinatorBinding> {
    @Override
    public int getLayoutId() {
        return R.layout.activity_coordinator;
    }

    @Override
    public void initView() {
        mBinding.toolbar.setTitle("这是标题");
        mBinding.detailImg.setImageResource(R.mipmap.landscape);



        mBinding.webview.loadUrl("https://m.ithome.com");
    }

    @Override
    public void initEvent() {
        mBinding.toolbar.setNavigationOnClickListener(v -> finish());
    }
}
