package com.tzy.demo.activity.largeimage;

import androidx.constraintlayout.utils.widget.ImageFilterView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.tzy.demo.R;

public class LargeImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_large_image);

        ImageFilterView imageFilterView = findViewById(R.id.iv);
        imageFilterView.setImageResource(R.mipmap.landscape);
    }
}