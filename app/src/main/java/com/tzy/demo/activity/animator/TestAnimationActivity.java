package com.tzy.demo.activity.animator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import com.tzy.demo.R;

public class TestAnimationActivity extends Activity {

    TextView mTvLottie;
    TextView mTvVector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_animation);
        mTvLottie = (TextView) findViewById(R.id.tv_lottie);
        mTvVector = (TextView) findViewById(R.id.tv_vector);

        mTvLottie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TestAnimationActivity.this, AnimatorActivity.class));
            }
        });

        mTvVector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TestAnimationActivity.this, VectorDrawableActivity.class));
            }
        });
    }
}
