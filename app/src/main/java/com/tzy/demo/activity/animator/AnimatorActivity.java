package com.tzy.demo.activity.animator;

import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.tzy.demo.R;
import com.tzy.demo.activity.base.BaseActivity;
import com.tzy.demo.databinding.ActivityAnimatorBinding;

public class AnimatorActivity extends BaseActivity<ActivityAnimatorBinding> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_animator;
    }

    @Override
    public void initView() {
        setSupportActionBar(mBinding.toolbar);
    }

    @Override
    public void initEvent() {

    }

    @Override
    protected int getMenuLayoutId() {
        return R.menu.animator_menu;
    }

    @Override
    protected boolean onMenuSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        mBinding.toolbar.setTitle(item.getTitle());
        if (id == R.id.svga) {
            showSVG();
        } else if (id == R.id.lottie) {
            showLottie();
        }
        return true;
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(mBinding.flContainer.getId(), fragment).commit();
    }

    private void showLottie() {
        Fragment fragment = LottieFragment.newInstance("anim/lottie/demo/images", "anim/lottie/data.json");
        replaceFragment(fragment);
    }

    private void showSVG() {
        Fragment fragment = SVGAFragment.newInstance("anim/svga/test.svga");
        replaceFragment(fragment);
    }
}
