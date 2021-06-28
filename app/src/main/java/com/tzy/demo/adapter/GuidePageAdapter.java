package com.tzy.demo.adapter;

import android.app.Activity;
import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.airbnb.lottie.LottieAnimationView;
import com.tzy.demo.R;

/**
 * Created by YANG
 * 2016/5/5 17:36
 * 向导页适配器
 */
public class GuidePageAdapter extends PagerAdapter {

    private LayoutInflater mInflater;
    private String mFolders[];
    private String mJsons[];

    public GuidePageAdapter(Activity activity) {
        mFolders = new String[] {"images1", "images2", "images3", "images4"};
        mJsons = new String[] {"page1.json", "page2.json", "page3.json", "page4.json"};
        this.mInflater = LayoutInflater.from(activity);
    }

    @Override
    public int getCount() {
        return mFolders.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LottieAnimationView view = (LottieAnimationView) mInflater.inflate(R.layout.lottie_item_layout, container, false);
        view.setImageAssetsFolder(mFolders[position]);
        view.setAnimation(mJsons[position]);
        container.addView(view);
        return view;
    }
    /*@Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mInflater.inflate(R.layout.lottie_item_layout, container, false);
        LottieAnimationView lottieView = view.findViewById(R.id.lottie_view);
        lottieView.setImageAssetsFolder(mFolders[position]);
        lottieView.setAnimation(mJsons[position]);
        lottieView.playAnimation();
        container.addView(view);
        return view;
    }*/

}
