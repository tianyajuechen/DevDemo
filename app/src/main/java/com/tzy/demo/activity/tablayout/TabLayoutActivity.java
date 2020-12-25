package com.tzy.demo.activity.tablayout;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.tzy.demo.R;
import com.tzy.demo.databinding.ActivityTabLayoutBinding;
import com.tzy.demo.fragment.TestFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TabLayoutActivity extends AppCompatActivity {

    ActivityTabLayoutBinding mBinding;
    List<String> tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_tab_layout);

        tabs = Arrays.asList("单曲", "场景音", "冥想", "解压");
        /*mBinding.vp.setAdapter(new MyAdapter(getSupportFragmentManager()));
        mBinding.tablayout.setupWithViewPager(mBinding.vp);*/
        for (int i = 0; i < tabs.size(); i++) {
            TabLayout.Tab tab = mBinding.tablayout.newTab();
            tab.setText(tabs.get(i));
            mBinding.tablayout.addTab(tab);
        }
    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return TestFragment.newInstance(tabs.get(i));
        }

        @Override
        public int getCount() {
            return tabs.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return tabs.get(position);
        }
    }
}