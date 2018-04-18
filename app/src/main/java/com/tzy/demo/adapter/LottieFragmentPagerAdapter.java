package com.tzy.demo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.tzy.demo.fragment.LottieFragment;

/**
 * Created by tang
 * 2018/4/18
 */
public class LottieFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private String mFolders[];
    private String mJsons[];

    public LottieFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        mFolders = new String[] {"images1", "images2", "images3", "images4"};
        mJsons = new String[] {"page1.json", "page2.json", "page3.json", "page4.json"};
    }

    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position
     */
    @Override
    public Fragment getItem(int position) {
        return LottieFragment.newInstance(mFolders[position], mJsons[position]);
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return mFolders.length;
    }
}
