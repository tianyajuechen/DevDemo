package com.tzy.demo.presenter;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by YANG
 * 2016/11/10 14:52
 */
public class Presenter1 {
    public void onFirstNameClick(View v) {
        String str = ((TextView) v).getText().toString();
        Toast.makeText(v.getContext(), str, Toast.LENGTH_SHORT).show();
    }

}
