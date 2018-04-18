package com.tzy.demo.activity.animator;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.tzy.demo.R;

public class ObjectAnimatorActivity extends AppCompatActivity {


    ImageView iv;

    Button bt;

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_animator);
        iv = (ImageView) findViewById(R.id.imageView);
        bt = (Button) findViewById(R.id.bt);
        tv = (TextView) findViewById(R.id.tv);

        //🤣😂😅😆

        bt.setText("button");
        tv.setText("textview");

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ObjectAnimator.ofFloat(iv, "rotation", 0f, 180f).setDuration(500).start();
                ObjectAnimator.ofFloat(iv, "translationX", 0f, 200f).setDuration(500).start();
                ObjectAnimator.ofFloat(iv, "translationY", 0f, 200f).setDuration(500).start();

                PropertyValuesHolder p1 = PropertyValuesHolder.ofFloat("rotation", 0f, 180f);
                PropertyValuesHolder p2 = PropertyValuesHolder.ofFloat("translationX", 0f, 180f);
                PropertyValuesHolder p3 = PropertyValuesHolder.ofFloat("translationY", 0f, 180f);
                ObjectAnimator.ofPropertyValuesHolder(iv, p1, p2, p3).setDuration(500).start();

                ObjectAnimator ani1 = ObjectAnimator.ofFloat(iv, "rotation", 0f, 270f);
                ObjectAnimator ani2 = ObjectAnimator.ofFloat(iv, "translationX", 0f, 270f);
                ObjectAnimator ani3 = ObjectAnimator.ofFloat(iv, "translationY", 0f, 270f);


            }
        });

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bt.performClick();
            }
        });
    }
}
