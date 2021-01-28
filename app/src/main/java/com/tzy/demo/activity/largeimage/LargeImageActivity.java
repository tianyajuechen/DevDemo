package com.tzy.demo.activity.largeimage;

import android.support.constraint.utils.ImageFilterView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.tzy.demo.R;

public class LargeImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_large_image);

        ImageFilterView imageFilterView = findViewById(R.id.iv);
        imageFilterView.setImageResource(R.mipmap.a);
    }
}