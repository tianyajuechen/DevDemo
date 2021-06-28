package com.tzy.demo.activity.animator;

import android.os.Bundle;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ImageView;


import com.tzy.demo.R;

public class VectorDrawableActivity extends AppCompatActivity {

    ImageView mIv1;
    ImageView mIv2;
    ImageView mIv3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vector_drawable);
        mIv1 = (ImageView) findViewById(R.id.iv1);
        mIv2 = (ImageView) findViewById(R.id.iv2);
        mIv3 = (ImageView) findViewById(R.id.iv3);

        ((AnimatedVectorDrawableCompat) mIv1.getDrawable()).start();
        ((AnimatedVectorDrawableCompat) mIv2.getDrawable()).start();
        ((AnimatedVectorDrawableCompat) mIv3.getDrawable()).start();
    }
}
