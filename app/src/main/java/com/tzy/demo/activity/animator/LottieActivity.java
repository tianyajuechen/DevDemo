package com.tzy.demo.activity.animator;

import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;


import com.tzy.demo.R;
import com.tzy.demo.adapter.LottieFragmentPagerAdapter;

public class LottieActivity extends AppCompatActivity {


    ViewPager mViewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottie);
        mViewpager = (ViewPager) findViewById(R.id.viewpager);

        mViewpager.setAdapter(new LottieFragmentPagerAdapter(getSupportFragmentManager()));
        mViewpager.setCurrentItem(0);
        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Toast.makeText(LottieActivity.this, position + "", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
