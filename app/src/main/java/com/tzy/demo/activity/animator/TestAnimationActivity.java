package com.tzy.demo.activity.animator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.tzy.demo.R;

public class TestAnimationActivity extends Activity {

    @BindView(R.id.tv_lottie)
    TextView mTvLottie;
    @BindView(R.id.tv_vector)
    TextView mTvVector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_animation);
        ButterKnife.bind(this);

        mTvLottie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TestAnimationActivity.this, LottieActivity.class));
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
