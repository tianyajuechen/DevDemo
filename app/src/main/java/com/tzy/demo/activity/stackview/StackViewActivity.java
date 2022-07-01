package com.tzy.demo.activity.stackview;

import android.os.Build;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.tzy.demo.R;
import com.tzy.demo.activity.base.BaseActivity;
import com.tzy.demo.databinding.ActivityStackViewBinding;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @Author tangzhaoyang
 * @Date 2021/7/27
 */
public class StackViewActivity extends BaseActivity<ActivityStackViewBinding> {

    private int[] mImages = new int[] {
            R.drawable.ic_girl_1,
            R.drawable.ic_girl_2,
            R.drawable.ic_girl_3,
            R.drawable.ic_girl_4,
            R.drawable.ic_girl_5,
            R.drawable.ic_girl_6
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_stack_view;
    }

    @Override
    public void initView() {
        Adapter adapter = new Adapter();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            adapter.setNewInstance(Arrays.stream(mImages).boxed().collect(Collectors.toList()));
        }
        mBinding.vp.setAdapter(adapter);
    }

    @Override
    public void initEvent() {

    }

    private class Adapter extends BaseQuickAdapter<Integer, BaseViewHolder> {

        public Adapter() {
            super(R.layout.item_stack);
        }

        @Override
        protected void convert(@NotNull BaseViewHolder holder, Integer integer) {
            holder.setImageResource(R.id.iv, integer);
        }
    }
}
