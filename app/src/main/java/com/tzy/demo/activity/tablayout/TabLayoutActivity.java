package com.tzy.demo.activity.tablayout;

import androidx.databinding.DataBindingUtil;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.tzy.demo.R;
import com.tzy.demo.databinding.ActivityTabLayoutBinding;
import com.tzy.demo.fragment.TestFragment;

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