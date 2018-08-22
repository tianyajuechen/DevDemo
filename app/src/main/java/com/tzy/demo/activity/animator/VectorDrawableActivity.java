package com.tzy.demo.activity.animator;

import android.os.Bundle;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.tzy.demo.R;

public class VectorDrawableActivity extends AppCompatActivity {

    @BindView(R.id.iv1)
    ImageView mIv1;
    @BindView(R.id.iv2)
    ImageView mIv2;
    @BindView(R.id.iv3)
    ImageView mIv3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vector_drawable);
        ButterKnife.bind(this);

        ((AnimatedVectorDrawableCompat) mIv1.getDrawable()).start();
        ((AnimatedVectorDrawableCompat) mIv2.getDrawable()).start();
        ((AnimatedVectorDrawableCompat) mIv3.getDrawable()).start();
    }
}
