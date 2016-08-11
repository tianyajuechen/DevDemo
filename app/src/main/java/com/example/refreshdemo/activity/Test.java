package com.example.refreshdemo.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.example.refreshdemo.R;

public class Test extends AppCompatActivity {

    @Bind(R.id.bt)
    Button mBt;
    @Bind(R.id.root_view)
    FrameLayout mRootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout);
        ButterKnife.bind(this);

        mBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar snackbar = Snackbar.make(mRootView, "你好啊啊啊", Snackbar.LENGTH_SHORT)
                        .setAction("do", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        })
                        .setActionTextColor(getResources().getColor(R.color.google_blue));
                snackbar.getView().setBackgroundColor(getResources().getColor(R.color.google_red));
                snackbar.show();
            }
        });
    }
}
